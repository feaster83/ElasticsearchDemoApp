package demoapp.student.repository.es;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.elasticsearch.common.geo.GeoPoint;

@Data
@AllArgsConstructor
public class ESStudent {
    private String name;
    private ESLocation city;
    private String birthday;

    @Data
    @AllArgsConstructor
    public static class ESLocation {
        private String name;
        private GeoPoint geoPoint;
    }
}
