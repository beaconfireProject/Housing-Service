package com.beaconfire.housingservice.model;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long houseId;

    @NotBlank
    private String type;

    private String description;

    @Min(0)
    private int quantity;
}