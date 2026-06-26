package org.varun.uberlocationservice.Services;

import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Service;
import org.varun.uberentityservice.Models.ExactLocation;
import org.varun.uberlocationservice.dtos.NearbyDriverRESPONSEdto;

import java.util.ArrayList;
import java.util.List;



@Service
public class LocationServiceImpl implements LocationService{

    private StringRedisTemplate redisTemplate;

    public LocationServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    private static final String DRIVER_GEO_OPS_KEY="drivers";
    private static final Double SEARCH_RADIUS=5.0;





    @Override
    public Boolean saveDriverLocation(Long driverId, Double latitude, Double longitude) {

        GeoOperations<String, String> geoOps = redisTemplate.opsForGeo();
        geoOps.add(
                DRIVER_GEO_OPS_KEY,
                new RedisGeoCommands.GeoLocation<String>(
                        driverId.toString(),
                        new Point(latitude,longitude)));
        return true;
    }

    @Override
    public List<NearbyDriverRESPONSEdto> nearByDrivers(Double latitude, Double longitude) {


        GeoOperations<String, String> geoOps= redisTemplate.opsForGeo();

        Distance radius= new Distance(SEARCH_RADIUS, Metrics.KILOMETERS);
        /// GeoResults
        ///     |
        ///     |--- GeoResult
        ///             |
        ///             |---- GeoLocation
        ///             |         |
        ///             |         |---- Driver Id
        ///             |
        ///             |---- Distance

        GeoResults< RedisGeoCommands.GeoLocation<String>> results =
                geoOps.search(
                        DRIVER_GEO_OPS_KEY,
                        GeoReference.fromCoordinate(latitude,longitude),
                        radius,
                        RedisGeoCommands.GeoSearchCommandArgs.newGeoSearchArgs()
                                .includeCoordinates()
                                .includeDistance()
                                .sortAscending()
                );

        List<NearbyDriverRESPONSEdto> drivers= new ArrayList<>();

        for (GeoResult< RedisGeoCommands.GeoLocation<String>> result: results){
            drivers.add(
                    NearbyDriverRESPONSEdto.builder()
                        .driverId(result.getContent().getName())
                        .distance(result.getDistance().getValue())
                        .currentLocation(
                                ExactLocation.builder()
                                     .latitude(result.getContent().getPoint().getX())
                                     .longitude(result.getContent().getPoint().getY())
                                .build())
                    .build());
        }


        return drivers;
    }
}
