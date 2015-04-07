package demoapp.location.service;

import demoapp.location.model.GeoLocation;
import demoapp.location.model.Location;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {
    private List<Location> locations;

    @Override
    public List<Location> getLocations() {
        return Collections.unmodifiableList(locations);
    }

    @PostConstruct
    private void createLocations() {
        locations = new ArrayList<>();
        locations.add(new Location("Hengelo", new GeoLocation(52.267155, 6.789706)));
        locations.add(new Location("Hellendoorn", new GeoLocation(52.388499, 6.449773)));
    }
}
