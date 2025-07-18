package com.beaconfire.housingservice.service.impl;

import com.beaconfire.housingservice.dao.FacilityReportRepository;
import com.beaconfire.housingservice.dao.FacilityRepository;
import com.beaconfire.housingservice.dto.*;
import com.beaconfire.housingservice.feign.EmployeeClient;
import com.beaconfire.housingservice.model.Facility;
import com.beaconfire.housingservice.model.FacilityReport;
import com.beaconfire.housingservice.service.FacilityReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class FacilityReportServiceImpl implements FacilityReportService {

    private final FacilityReportRepository reportRepository;
    private final EmployeeClient employeeClient;
    private final FacilityRepository facilityRepository;

    public FacilityReportServiceImpl(FacilityReportRepository reportRepository,
                                     EmployeeClient employeeClient,
                                     FacilityRepository facilityRepository) {
        this.reportRepository = reportRepository;
        this.employeeClient = employeeClient;
        this.facilityRepository = facilityRepository;
    }

    @Override
    public FacilityReport submitFacilityReport(String userId, FacilityReportRequest request) {
        if (request.getFacilityId() == null) {
            throw new IllegalArgumentException("Please provide a facility ID.");
        }

        Long houseId = getEmployeeHouseId(userId);

        Facility facility = facilityRepository.findById(request.getFacilityId())
                .orElseThrow(() -> new IllegalArgumentException("That facility does not exist."));

        if (!facility.getHouseId().equals(houseId)) {
            throw new IllegalArgumentException("You are not allowed to report on a facility that belongs to another house.");
        }

        EmployeeResponse employee = employeeClient.getEmployeeByUserId(userId).getData();
        String employeeId = employee.getId();

        FacilityReport report = new FacilityReport();
        report.setFacility(facility);
        report.setCreateDate(LocalDateTime.now());
        report.setStatus("Submitted");
        report.setTitle(request.getTitle());
        report.setDescription(request.getDescription());
        report.setEmployeeId(employeeId);

        return reportRepository.save(report);
    }






    @Override
    public List<FacilityReport> getFacilityReportsForEmployeeHouse(String userId) {
        Long houseId = getEmployeeHouseId(userId);
        return reportRepository.findByFacility_HouseId(houseId);
    }

    @Override
    public List<FacilityReport> getFacilityReportsByHouseId(Long houseId) {
        return reportRepository.findByFacility_HouseId(houseId);
    }

    @Override
    public FacilityReport getFacilityReportById(Long reportId) {
        return reportRepository.findById(reportId).orElse(null);
    }

    @Override
    public Page<FacilityReportDTO> getFacilityReportsByHouseId(Long houseId, Pageable pageable) {
        Page<FacilityReport> reports = reportRepository.findByHouseIdNative(houseId, pageable);

        return reports.map(report -> {
            String reporterName = "Unknown";
            try {
                EmployeeResponseWrapper wrapper = employeeClient.getEmployeeByEmployeeId(report.getEmployeeId());
                if (wrapper != null && wrapper.getData() != null) {
                    EmployeeResponse data = wrapper.getData();
                    reporterName = data.getFirstName() + " " + data.getLastName();
                }
            } catch (Exception e) {
                System.out.println("Failed to get reporter name for employeeId");
            }

            Facility facility = report.getFacility();
            FacilityDTO facilityDTO = FacilityDTO.builder()
                    .type(facility.getType())
                    .description(facility.getDescription())
                    .quantity(facility.getQuantity())
                    .build();

            FacilityReportDTO dto = FacilityReportDTO.builder()
                    .id(report.getId())
                    .title(report.getTitle())
                    .description(report.getDescription())
                    .status(report.getStatus())
                    .createDate(report.getCreateDate().toString())
                    .reporterName(reporterName)
                    .employeeId(report.getEmployeeId())
                    .facility(facilityDTO)
                    .build();

            return dto;
        });
    }

    private Long getEmployeeHouseId(String employeeId) {
        EmployeeResponseWrapper wrapper = employeeClient.getEmployeeByUserId(employeeId);

        if (wrapper == null || wrapper.getData() == null || wrapper.getData().getHouseId() == null) {
            throw new RuntimeException("Employee or assigned house not found");
        }

        try {
            return Long.parseLong(wrapper.getData().getHouseId());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid houseId format: " + wrapper.getData().getHouseId());
        }
    }

    @Override
    public void updateFacilityReportStatus(Long reportId, String status) {
        List<String> allowedStatuses = Arrays.asList("Open", "In Progress", "Closed");
        if (!allowedStatuses.contains(status)) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }

        FacilityReport report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Facility Report not found"));

        report.setStatus(status);
        reportRepository.save(report);
    }





}
