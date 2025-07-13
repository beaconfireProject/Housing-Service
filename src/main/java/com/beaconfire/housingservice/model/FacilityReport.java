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
public class FacilityReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long facilityId;

    @NotNull
    private Long employeeId;

    @NotBlank
    private String title;

    private String description;

    private LocalDate createDate;

    @NotBlank
    private String status;
}