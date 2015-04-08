package demoapp.student.service;

import demoapp.student.model.Student;

public interface StudentServiceEventListener {
    public void studentAdded(Student student);
    public void studentRemoved(Student student);
    public void studentUpdated(Student student);
}
