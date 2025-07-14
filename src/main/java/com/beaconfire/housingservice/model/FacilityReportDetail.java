package com.beaconfire.housingservice.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "FacilityReportDetail", schema = "HR_Portal")
public class FacilityReportDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "FacilityReportID")
    private Long facilityReportId;

    @NotNull
    @Column(name = "EmployeeID")
    private Long employeeId;

    @NotNull
    @Column(name = "Comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "CreateDate")
    private LocalDateTime createDate;

    @Column(name = "LastModificationDate")
    private LocalDateTime lastModificationDate;
}
