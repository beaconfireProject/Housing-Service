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
@Table(name = "FacilityReport", schema = "HR_Portal")
public class FacilityReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FacilityID", insertable = false, updatable = false)
    private Long facilityId;

    @NotNull
    @Column(name = "EmployeeID")
    private String employeeId;

    @NotBlank
    @Size(max = 100)
    @Column(name = "Title")
    private String title;

    @Column(name = "Description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "CreateDate")
    private LocalDateTime createDate;

    @NotBlank
    @Size(max = 50)
    @Column(name = "Status")
    private String status = "Open";

    @ManyToOne
    @JoinColumn(name = "FacilityID", referencedColumnName = "ID")
    private Facility facility;
}
