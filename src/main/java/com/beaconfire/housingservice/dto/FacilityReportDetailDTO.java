package com.beaconfire.housingservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FacilityReportDetailDTO {
    private String comment;
    private String createdBy;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;
}
