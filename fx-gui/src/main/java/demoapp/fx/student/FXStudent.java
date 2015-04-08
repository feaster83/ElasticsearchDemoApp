package demoapp.fx.student;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class FXStudent {
    private String id;
    private String name;
    private String city;
    private LocalDate birthday;
}
