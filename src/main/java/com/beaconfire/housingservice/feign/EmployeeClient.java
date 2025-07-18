package com.beaconfire.housingservice.feign;

import com.beaconfire.housingservice.dto.EmployeeListResponseWrapper;
import com.beaconfire.housingservice.dto.EmployeeResponse;
import com.beaconfire.housingservice.dto.EmployeeResponseWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "employee-service")
public interface EmployeeClient {

    @GetMapping("/api/employees/userId/{id}")
    EmployeeResponseWrapper getEmployeeByUserId(@PathVariable("id") String id);

    @GetMapping("/api/employees/houseId/{houseId}")
    EmployeeListResponseWrapper getEmployeesByHouseId(@PathVariable("houseId") Long houseId);

    @PatchMapping("/api/employees/{id}")
    void patchEmployee(@PathVariable("id") String employeeId, @RequestBody Map<String, Object> updates);

    @GetMapping("/api/employees/{id}")
    EmployeeResponseWrapper getEmployeeByEmployeeId(@PathVariable("id") String employeeId);
}
