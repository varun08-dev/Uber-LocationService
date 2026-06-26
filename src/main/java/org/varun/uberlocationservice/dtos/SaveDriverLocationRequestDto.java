package org.varun.uberlocationservice.dtos;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveDriverLocationRequestDto {

    private Long driverId;
    private Double latitude;
    private Double longitude;
}
