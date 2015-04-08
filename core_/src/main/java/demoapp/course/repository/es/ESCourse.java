package demoapp.course.repository.es;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ESCourse {
    private String name;
    private Float price;
}
