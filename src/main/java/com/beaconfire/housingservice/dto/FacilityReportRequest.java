package com.beaconfire.housingservice.dto;

import lombok.Data;

@Data
public class FacilityReportRequest {
    private Long facilityId;
    private String title;
    private String description;
    private String status;
}

