package demoapp.fx.course;

import demoapp.course.model.Course;

public class FXCourseMapper {

    public static FXCourse getCourse(Course course) {
        return new FXCourse(course.getId(), course.getName(), course.getPrice().toString());
    }
}
