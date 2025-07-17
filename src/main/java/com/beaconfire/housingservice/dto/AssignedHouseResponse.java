package com.beaconfire.housingservice.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignedHouseResponse {
    private Long houseId;
    private String address;
    private List<OccupantInfo> occupants;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OccupantInfo {
        private String name;
        private String phone;
    }
}
