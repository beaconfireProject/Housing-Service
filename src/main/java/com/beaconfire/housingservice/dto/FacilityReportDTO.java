package com.beaconfire.housingservice.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class FacilityReportDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String createDate;
    private String reporterName;
    private String employeeId;
    private FacilityDTO facility;
}
