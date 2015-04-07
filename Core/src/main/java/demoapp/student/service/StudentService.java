package demoapp.student.service;

import demoapp.student.model.Student;

import java.util.List;

public interface StudentService {
    Student addStudent(Student course);
    void deleteStudent(String id);
    Student getStudent(String id);
    List<Student> getStudents();
    void addStudentEventListener(StudentServiceEventListener listener);
}
