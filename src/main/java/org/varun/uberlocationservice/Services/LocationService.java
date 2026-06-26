package org.varun.uberlocationservice.Services;

import org.varun.uberlocationservice.dtos.NearbyDriverRESPONSEdto;

import java.util.List;

public interface LocationService {

    Boolean saveDriverLocation (Long driverId,Double latitude,Double longitude);


    List<NearbyDriverRESPONSEdto> nearByDrivers(Double latitude,Double longitude);
}
