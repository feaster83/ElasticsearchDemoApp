package demoapp.course.service;

import demoapp.course.model.Course;

import java.util.List;

public interface CourseService {
    Course addCourse(Course course);
    void deleteCourse(String id);
    Course getCourse(String id);
    List<Course> getCourses();
    void addCourseEventListener(CourseServiceEventListener listener);
}
