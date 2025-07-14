package com.beaconfire.housingservice.service;

import com.beaconfire.housingservice.model.FacilityReport;

import java.util.List;

public interface FacilityReportService {
    FacilityReport submitFacilityReport(FacilityReport report);
    List<FacilityReport> getFacilityReportsForEmployeeHouse(Long employeeId);
    List<FacilityReport> getFacilityReportsByHouseId(Long houseId);
    FacilityReport getFacilityReportById(Long reportId);
}
