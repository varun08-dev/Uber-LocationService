package org.varun.uberlocationservice.Controllers;


import org.springframework.data.geo.*;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.varun.uberlocationservice.Services.LocationService;
import org.varun.uberlocationservice.dtos.NearByDriverRequestDTO;
import org.varun.uberlocationservice.dtos.NearbyDriverRESPONSEdto;
import org.varun.uberlocationservice.dtos.SaveDriverLocationRequestDto;
import java.util.List;

@RestController
@RequestMapping("/api/locations")
public class LocationController {


    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/drivers")
    public ResponseEntity<Boolean> saveDriverLocation(@RequestBody SaveDriverLocationRequestDto request){

        try {
            Boolean response= locationService.saveDriverLocation(request.getDriverId(), request.getLatitude(), request.getLongitude());
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @GetMapping("/nearby/Drivers")
    public ResponseEntity<?> nearByDrivers(@RequestBody NearByDriverRequestDTO request){
        List<NearbyDriverRESPONSEdto> drivers = null;
       try {
           drivers  = locationService.nearByDrivers(request.getLatitude(), request.getLongitude());
           return new ResponseEntity<>(drivers,HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(drivers,HttpStatus.NOT_FOUND);
       }
    }
}
