package com.beaconfire.housingservice.dto;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseDetailsResponse {
    private Long houseId;
    private String address;
    private LandlordInfo landlord;
    private int numEmployees;
    private List<EmployeeSummary> employees;
    private Map<String, Integer> facilities;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LandlordInfo {
        private String firstName;
        private String lastName;
        private String phone;
        private String email;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EmployeeSummary {
        private String name;
        private String phone;
        private String email;
    }
}
