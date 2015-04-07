package demoapp.course.service;

import demoapp.course.model.Course;

public interface CourseServiceEventListener {
    public void courseAdded(Course course);
    public void courseRemoved(Course course);
    public void courseUpdated(Course course);
}
