package demoapp.course.webservice;

import demoapp.course.model.Course;
import demoapp.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.Assert.isNull;

@RestController
@RequestMapping("/course")
public class CourseWebService {

    private CourseService courseService;

    @Autowired
    public CourseWebService(CourseService courseService) {
        this.courseService = courseService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Course> getCourses() {
        return courseService.getCourses();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Course getCourse(@PathVariable String id) {
        return courseService.getCourse(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Course addCourse(@RequestBody Course course) {
        isNull(course.getId(), "ID should be null");

        return courseService.addCourse(course);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
    }
}
