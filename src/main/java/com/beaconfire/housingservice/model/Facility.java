package com.beaconfire.housingservice.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Facility", schema = "HR_Portal")
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "HouseID")
    private Long houseId;

    @NotBlank
    @Column(name = "Type")
    private String type;

    @Column(name = "Description")
    private String description;

    @Min(0)
    @Column(name = "Quantity")
    private int quantity;
}
