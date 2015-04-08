package demoapp;

import demoapp.course.service.CourseService;
import demoapp.location.service.LocationService;
import demoapp.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class ServiceLocator {

    private static ServiceLocator instance;

    @Autowired private CourseService courseService;
    @Autowired private StudentService studentService;
    @Autowired private LocationService locationService;

    @PostConstruct
    private void createInstance() {
        instance = this;
    }

    public static CourseService getCourseService() {
        return instance.courseService;
    }
    public static StudentService getStudentService() {
        return instance.studentService;
    }
    public static LocationService getLocationService() {
        return instance.locationService;
    }
}
