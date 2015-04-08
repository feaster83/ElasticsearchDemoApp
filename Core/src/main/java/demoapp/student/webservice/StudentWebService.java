package demoapp.student.webservice;

import demoapp.student.model.Student;
import demoapp.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.Assert.isNull;

@RestController
@RequestMapping("/student")
public class StudentWebService {

    private StudentService studentService;

    @Autowired
    public StudentWebService(StudentService studentService) {
        this.studentService = studentService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Student getStudent(@PathVariable String id) {
        return studentService.getStudent(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Student addStudent(@RequestBody Student student) {
        isNull(student.getId(), "ID should be null");

        return studentService.addStudent(student);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
    }
}
