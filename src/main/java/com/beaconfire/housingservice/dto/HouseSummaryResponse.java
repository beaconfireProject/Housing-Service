package com.beaconfire.housingservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HouseSummaryResponse {
    private Long houseId;
    private String address;
    private int numEmployees; // mock with 0
    private LandlordInfo landlord;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LandlordInfo {
        private String firstName;
        private String lastName;
        private String phone;
        private String email;
    }
}
