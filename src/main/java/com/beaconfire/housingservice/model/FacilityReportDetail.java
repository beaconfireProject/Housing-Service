package com.beaconfire.housingservice.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FacilityReportDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long facilityReportId;

    @NotNull
    private Long employeeId;

    private String comment;

    private LocalDate createDate;

    private LocalDate lastModificationDate;
}