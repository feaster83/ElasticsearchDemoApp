package demoapp.course.repository.es;

import demoapp.course.model.Course;

public final class CourseMapper {
    private CourseMapper(){};

    public static ESCourse toESCourse(Course course) {
        return new ESCourse(course.getName(), course.getPrice());
    }

    public static Course toCourse(String id, ESCourse course) {
        return new Course(id, course.getName(), course.getPrice());
    }
}
