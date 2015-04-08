package demoapp.student.repository.es;

import com.google.gson.Gson;
import demoapp.RepositoryException;
import demoapp.student.model.Student;
import demoapp.student.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.types.TypesExistsRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static demoapp.Exceptions.ExceptionMessage.*;
import static demoapp.Exceptions.getMessage;

@Slf4j
@Repository
public class ESStudentRepository implements StudentRepository {
    private final Gson gson = new Gson();
    private final String INDEX = "demoapp";
    private final String TYPE = "student";
    private Client client;

    @Autowired
    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public Student addStudent(Student student) {
        ESStudent esStudent = StudentMapper.toESStudent(student);

        try {
            IndexResponse indexResponse = client.prepareIndex(INDEX, TYPE)
                   .setSource(gson.toJson(esStudent))
                   .execute()
                   .actionGet();

           if (indexResponse.isCreated()) {
               return getStudent(indexResponse.getId());
           } else {
               log.error(getMessage(REPOSITORY_ADD_ITEM_FAILED, student));
               throw new RepositoryException(getMessage(REPOSITORY_ADD_ITEM_FAILED, student));
           }
        } catch (ElasticsearchException e) {
            log.error(getMessage(REPOSITORY_ADD_ITEM_FAILED_UNEXPECTED, student, e.getDetailedMessage()));
            throw new RepositoryException(
                    getMessage(REPOSITORY_ADD_ITEM_FAILED_UNEXPECTED, student, e.getDetailedMessage()),
                    e.getRootCause());
        }
    }

    @Override
    public List<Student> getStudents() {
        try {
            List<Student> returnList = new ArrayList<>();
            if (typeExists(INDEX, TYPE)) {
                SearchResponse searchResponse = client.prepareSearch(INDEX)
                        .setTypes(TYPE)
                        .setSize(Integer.MAX_VALUE)
                        .execute()
                        .actionGet();

                searchResponse.getHits().forEach(hit -> {
                    ESStudent returnedStudent = gson.fromJson(hit.getSourceAsString(), ESStudent.class);
                    returnList.add(StudentMapper.toStudent(hit.getId(), returnedStudent));
                });
            }
            return returnList;

        } catch (ElasticsearchException e) {
            throw new RepositoryException(getMessage(REPOSITORY_RESOLVE_ITEMS_FAILED_UNEXPECTED, e.getDetailedMessage()),
                    e.getRootCause());
        }
    }

    public boolean typeExists(String index, String type) {
        boolean indexExist = client.admin().indices().exists(new IndicesExistsRequest(index)).actionGet().isExists();
        boolean typeExist = false;
        if (indexExist) {
            String[] indexArray = new String[1];
            indexArray[0] = INDEX;
            typeExist = client.admin().indices().typesExists(new TypesExistsRequest(indexArray, type)).actionGet().isExists();
        }
        return typeExist;
    }

    public Student getStudent(String id) {
        GetResponse getResponse = client.prepareGet(INDEX, TYPE, id).execute().actionGet();
        ESStudent returnedStudent = gson.fromJson(getResponse.getSourceAsString(), ESStudent.class);
        return StudentMapper.toStudent(getResponse.getId(), returnedStudent);
    }
}
