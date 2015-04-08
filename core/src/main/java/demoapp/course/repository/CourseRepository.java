package demoapp.course.repository;

import demoapp.course.model.Course;

import java.util.List;

public interface CourseRepository {
    Course addCourse(Course course);
    Course getCourse(String id);
    List<Course> getCourses();
}
