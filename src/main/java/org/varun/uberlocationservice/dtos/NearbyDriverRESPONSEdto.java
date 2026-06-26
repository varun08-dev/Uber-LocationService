package org.varun.uberlocationservice.dtos;


import lombok.*;
import org.varun.uberentityservice.Models.ExactLocation;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NearbyDriverRESPONSEdto {

    private String driverId;

    private Double distance;

    private ExactLocation currentLocation;
}
