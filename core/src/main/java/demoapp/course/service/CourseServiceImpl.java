package demoapp.course.service;

import demoapp.ServiceException;
import demoapp.course.model.Course;
import demoapp.course.repository.CourseRepository;
import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static demoapp.Exceptions.ExceptionMessage.SERVICE_ADD_NOT_ALLOWED_NOT_NEW;
import static demoapp.Exceptions.ExceptionMessage.SERVICE_INVALID_ARGUMENT;
import static demoapp.Exceptions.getMessage;

@Service
public class CourseServiceImpl implements CourseService {
    private List<CourseServiceEventListener> listeners = new ArrayList<>();
    private CourseRepository courseRepository;

    @Autowired
    public void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course addCourse(Course course) {
        if (course != null) {
            if (StringUtils.isBlank(course.getId())) {
                Course addedCourse = courseRepository.addCourse(course);
                listeners.parallelStream().forEach(listener -> listener.courseAdded(addedCourse));
                return addedCourse;
            } else {
                throw new ServiceException(getMessage(SERVICE_ADD_NOT_ALLOWED_NOT_NEW, course));
            }
        }
        throw new ServiceException(getMessage(SERVICE_INVALID_ARGUMENT));
    }

    @Override
    public void deleteCourse(String id) {

    }

    @Override
    public Course getCourse(String id) {
        Course course = courseRepository.getCourse(id);
        return course;
    }

    @Override
    public List<Course> getCourses() {
       List<Course> courses = courseRepository.getCourses();
       return courses;
    }

    public void addCourseEventListener(CourseServiceEventListener listener) {
        listeners.add(listener);
    }
}
