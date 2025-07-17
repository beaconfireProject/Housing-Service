package com.beaconfire.housingservice.dto;


import lombok.Data;

@Data
public class AssignHouseRequest {
    private String employeeId;
    private Long houseId;
}