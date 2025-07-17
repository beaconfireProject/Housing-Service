package com.beaconfire.housingservice.dto;

import lombok.Data;
import java.util.List;

@Data
public class EmployeeListResponseWrapper {
    private List<EmployeeResponse> data;
}
