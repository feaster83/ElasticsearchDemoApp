package demoapp.location.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Location {
    private String name;
    private GeoLocation geoLocation;

    @Override
    public String toString() {
        return name;
    }
}
