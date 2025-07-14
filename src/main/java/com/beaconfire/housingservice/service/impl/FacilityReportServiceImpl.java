package com.beaconfire.housingservice.service.impl;

import com.beaconfire.housingservice.dao.FacilityReportRepository;
import com.beaconfire.housingservice.model.FacilityReport;
import com.beaconfire.housingservice.service.FacilityReportService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityReportServiceImpl implements FacilityReportService {

    private final FacilityReportRepository reportRepository;

    public FacilityReportServiceImpl(FacilityReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public FacilityReport submitFacilityReport(FacilityReport report) {
        // TODO: implement logic to save report
        return reportRepository.save(report);
    }

    @Override
    public List<FacilityReport> getFacilityReportsForEmployeeHouse(Long employeeId) {
        // TODO: implement logic to return reports for employee's house
        return null;
    }

    @Override
    public List<FacilityReport> getFacilityReportsByHouseId(Long houseId) {
        return reportRepository.findByFacility_HouseId(houseId);
    }

    @Override
    public FacilityReport getFacilityReportById(Long reportId) {
        // TODO: implement logic to fetch report by ID
        return reportRepository.findById(reportId).orElse(null);
    }

}
