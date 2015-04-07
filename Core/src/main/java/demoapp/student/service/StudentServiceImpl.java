package demoapp.student.service;

import demoapp.ServiceException;
import demoapp.student.model.Student;
import demoapp.student.repository.StudentRepository;
import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static demoapp.Exceptions.ExceptionMessage.SERVICE_ADD_NOT_ALLOWED_NOT_NEW;
import static demoapp.Exceptions.ExceptionMessage.SERVICE_INVALID_ARGUMENT;
import static demoapp.Exceptions.getMessage;

@Service
public class StudentServiceImpl implements StudentService {
    private List<StudentServiceEventListener> listeners = new ArrayList<>();
    private StudentRepository studentRepository;

    @Autowired
    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        if (student != null) {
            if (StringUtils.isBlank(student.getId())) {
                Student addedStudent = studentRepository.addStudent(student);
                listeners.parallelStream().forEach(listener -> listener.studentAdded(addedStudent));
                return addedStudent;
            } else {
                throw new ServiceException(getMessage(SERVICE_ADD_NOT_ALLOWED_NOT_NEW, student));
            }
        }
        throw new ServiceException(getMessage(SERVICE_INVALID_ARGUMENT));
    }

    @Override
    public void deleteStudent(String id) {

    }

    @Override
    public Student getStudent(String id) {
        Student student = studentRepository.getStudent(id);
        return student;
    }

    @Override
    public List<Student> getStudents() {
       List<Student> students = studentRepository.getStudents();
       return students;
    }

    public void addStudentEventListener(StudentServiceEventListener listener) {
        listeners.add(listener);
    }
}
