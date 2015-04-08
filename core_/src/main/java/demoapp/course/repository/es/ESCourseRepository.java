package demoapp.course.repository.es;

import com.google.gson.Gson;
import demoapp.RepositoryException;
import demoapp.course.model.Course;
import demoapp.course.repository.CourseRepository;
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

@Repository
public class ESCourseRepository implements CourseRepository {
    private final Gson gson = new Gson();
    private final String INDEX = "demoapp";
    private final String TYPE = "course";
    private Client client;

    @Autowired
    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public Course addCourse(Course course) {
        ESCourse esCourse = CourseMapper.toESCourse(course);

        try {
            IndexResponse indexResponse = client.prepareIndex(INDEX, TYPE)
                    .setSource(gson.toJson(esCourse))
                    .execute()
                    .actionGet();

            if (indexResponse.isCreated()) {
                return getCourse(indexResponse.getId());
            } else {
                throw new RepositoryException(getMessage(REPOSITORY_ADD_ITEM_FAILED, course));
            }
        } catch (ElasticsearchException e) {
            throw new RepositoryException(
                    getMessage(REPOSITORY_ADD_ITEM_FAILED_UNEXPECTED, course, e.getDetailedMessage()),
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

    @Override
    public List<Course> getCourses() {
        try {
            List<Course> returnList = new ArrayList<>();
            if (typeExists(INDEX, TYPE)) {

                SearchResponse searchResponse = client.prepareSearch(INDEX)
                        .setTypes(TYPE)
                        .setSize(Integer.MAX_VALUE)
                        .execute()
                        .actionGet();

                searchResponse.getHits().forEach(hit -> {
                    ESCourse returnedCourse = gson.fromJson(hit.getSourceAsString(), ESCourse.class);
                    returnList.add(CourseMapper.toCourse(hit.getId(), returnedCourse));
                });
            }
            return returnList;

        } catch (ElasticsearchException e) {
            throw new RepositoryException(getMessage(REPOSITORY_RESOLVE_ITEMS_FAILED_UNEXPECTED, e.getDetailedMessage()),
                    e.getRootCause());
        }
    }

    public Course getCourse(String id) {
        GetResponse getResponse = client.prepareGet(INDEX, TYPE, id).execute().actionGet();
        ESCourse returnedCourse = gson.fromJson(getResponse.getSourceAsString(), ESCourse.class);
        return CourseMapper.toCourse(getResponse.getId(), returnedCourse);
    }
}
