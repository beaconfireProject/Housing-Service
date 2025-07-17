package com.beaconfire.housingservice.dto;

import lombok.Data;

@Data
public class EmployeeResponseWrapper {
    private boolean success;
    private String timestamp;
    private EmployeeResponse data;
    private String message;
}