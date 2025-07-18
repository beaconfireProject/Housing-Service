package com.beaconfire.housingservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FacilityDTO {
    private String type;
    private String description;
    private int quantity;
}
