package demoapp.fx.student;

import demoapp.student.model.Student;

public class FXStudentMapper {

    public static FXStudent getStudent(Student student) {
       return new FXStudent(student.getId(), student.getName(), student.getCity().getName(), student.getBirthday());
    }
}
