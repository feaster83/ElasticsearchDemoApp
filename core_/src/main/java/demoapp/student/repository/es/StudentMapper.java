package demoapp.student.repository.es;

import demoapp.location.model.GeoLocation;
import demoapp.location.model.Location;
import demoapp.student.model.Student;
import org.elasticsearch.common.geo.GeoPoint;

import java.time.LocalDate;

public final class StudentMapper {
    private StudentMapper(){};

    public static ESStudent toESStudent(Student student) {
        Location studentCity = student.getCity();
        GeoLocation studentCityLoc = studentCity.getGeoLocation();
        GeoPoint geoPoint = new GeoPoint(studentCityLoc.getLatitude(), studentCityLoc.getLongitude());
        ESStudent.ESLocation esLocation = new ESStudent.ESLocation(studentCity.getName(), geoPoint);
        return new ESStudent(student.getName(), esLocation, student.getBirthday().toString());
    }

    public static Student toStudent(String id, ESStudent student) {
        GeoLocation geoLocation = new GeoLocation(student.getCity().getGeoPoint().getLat(), student.getCity().getGeoPoint().getLon());
        Location cityLoc = new Location(student.getCity().getName(), geoLocation);
        return new Student(id, student.getName(), cityLoc, LocalDate.parse(student.getBirthday()));
    }
}
