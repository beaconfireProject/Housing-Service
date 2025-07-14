package com.beaconfire.housingservice.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "House", schema = "HR_Portal")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotNull
    @Column(name = "LandlordID")
    private Long landlordId;

    @NotBlank
    @Column(name = "Address")
    private String address;

    @Min(1)
    @Column(name = "MaxOccupant")
    private int maxOccupant;
}
