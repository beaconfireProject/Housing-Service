package com.beaconfire.housingservice.service.impl;

import com.beaconfire.housingservice.dao.FacilityReportDetailRepository;
import com.beaconfire.housingservice.dao.FacilityReportRepository;
import com.beaconfire.housingservice.dto.EmployeeResponse;
import com.beaconfire.housingservice.exception.ResourceNotFoundException;
import com.beaconfire.housingservice.feign.EmployeeClient;
import com.beaconfire.housingservice.model.FacilityReportDetail;
import com.beaconfire.housingservice.security.AuthDetails;
import com.beaconfire.housingservice.service.FacilityReportDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FacilityReportDetailServiceImpl implements FacilityReportDetailService {

    private final FacilityReportDetailRepository facilityReportDetailRepository;
    private final FacilityReportRepository facilityReportRepository;
    private final EmployeeClient employeeClient;


    @Autowired
    public FacilityReportDetailServiceImpl(FacilityReportDetailRepository facilityReportDetailRepository,
                                           FacilityReportRepository facilityReportRepository,
                                           EmployeeClient employeeClient) {
        this.facilityReportDetailRepository = facilityReportDetailRepository;
        this.facilityReportRepository = facilityReportRepository;
        this.employeeClient = employeeClient;
    }


    @Override
    public Page<FacilityReportDetail> getFacilityReportDetailsByReportId(Long reportId, Pageable pageable) {
        return facilityReportDetailRepository.findByFacilityReportId(reportId, pageable);
    }

    @Override
    public FacilityReportDetail addHRComment(Long reportId, FacilityReportDetail detail) {
        if (!facilityReportRepository.existsById(reportId)) {
            throw new ResourceNotFoundException("Facility Report with ID " + reportId + " not found");
        }

        // Get current authenticated user's details
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthDetails details = (AuthDetails) auth.getDetails();
        String role = details.getRole();
        String userId = String.valueOf(details.getUserId());  // Convert Long to String if needed

        // Set employeeId based on role
        if ("EMPLOYEE".equalsIgnoreCase(role)) {
            EmployeeResponse employee = employeeClient.getEmployeeByUserId(userId).getData();
            detail.setEmployeeId(employee.getId());
        } else {
            detail.setEmployeeId(null); // HR doesn't have employeeId
        }

        detail.setFacilityReportId(reportId);
        detail.setCreateDate(LocalDateTime.now());
        detail.setLastModificationDate(null);

        return facilityReportDetailRepository.save(detail);

    }

    @Override
    public void updateHRComment(Long commentId, String newComment) {
        FacilityReportDetail detail = facilityReportDetailRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        detail.setComment(newComment);
        detail.setLastModificationDate(LocalDateTime.now());

        facilityReportDetailRepository.save(detail);
    }

    @Override
    public FacilityReportDetail addEmployeeComment(Long reportId, FacilityReportDetail detail, String userId) {
        if (!facilityReportRepository.existsById(reportId)) {
            throw new ResourceNotFoundException("Facility Report with ID " + reportId + " not found");
        }

        // Set the reporting employee ID
        EmployeeResponse employee = employeeClient.getEmployeeByUserId(userId).getData();
        detail.setEmployeeId(employee.getId());

        detail.setFacilityReportId(reportId);
        detail.setCreateDate(LocalDateTime.now());
        detail.setLastModificationDate(null);

        return facilityReportDetailRepository.save(detail);
    }

    @Override
    public String updateEmployeeComment(Long commentId, FacilityReportDetail newDetail) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthDetails details = (AuthDetails) auth.getDetails();
        String userId = String.valueOf(details.getUserId());

        FacilityReportDetail existing = facilityReportDetailRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));

        // Get employee ID associated with this user
        String employeeId = employeeClient.getEmployeeByUserId(userId).getData().getId();

        // Make sure user owns this comment
        if (!employeeId.equals(existing.getEmployeeId())) {
            throw new RuntimeException("Unauthorized to update this comment");
        }

        // Update the comment
        existing.setComment(newDetail.getComment());
        existing.setLastModificationDate(LocalDateTime.now());
        facilityReportDetailRepository.save(existing);

        return "Comment updated";
    }

    @Override
    public List<FacilityReportDetail> getAllCommentsForReport(Long reportId) {
        return facilityReportDetailRepository.findByFacilityReportId(reportId);
    }


}
