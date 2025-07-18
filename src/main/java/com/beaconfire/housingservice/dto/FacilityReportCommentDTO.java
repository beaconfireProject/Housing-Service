package com.beaconfire.housingservice.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FacilityReportCommentDTO {
    private Long id;
    private String comment;
    private String createdBy;
    private LocalDateTime createDate;
    private LocalDateTime lastModifiedDate;
}
