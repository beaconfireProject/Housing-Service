package com.beaconfire.housingservice.service;

import com.beaconfire.housingservice.dto.FacilityReportDTO;
import com.beaconfire.housingservice.dto.FacilityReportRequest;
import com.beaconfire.housingservice.model.FacilityReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FacilityReportService {
    FacilityReport submitFacilityReport(String userId, FacilityReportRequest request);
    List<FacilityReport> getFacilityReportsForEmployeeHouse(String employeeId);
    List<FacilityReport> getFacilityReportsByHouseId(Long houseId);
    FacilityReport getFacilityReportById(Long reportId);
    Page<FacilityReportDTO> getFacilityReportsByHouseId(Long houseId, Pageable pageable);
    void updateFacilityReportStatus(Long reportId, String status);

}
