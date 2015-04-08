package demoapp.student.repository;

import demoapp.student.model.Student;

import java.util.List;

public interface StudentRepository {
    Student addStudent(Student course);
    Student getStudent(String id);
    List<Student> getStudents();
}
