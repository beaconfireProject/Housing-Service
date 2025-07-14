package com.beaconfire.housingservice.service.impl;

import com.beaconfire.housingservice.dao.FacilityReportDetailRepository;
import com.beaconfire.housingservice.dao.FacilityReportRepository;
import com.beaconfire.housingservice.exception.ResourceNotFoundException;
import com.beaconfire.housingservice.model.FacilityReportDetail;
import com.beaconfire.housingservice.service.FacilityReportDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FacilityReportDetailServiceImpl implements FacilityReportDetailService {

    private final FacilityReportDetailRepository facilityReportDetailRepository;
    private final FacilityReportRepository facilityReportRepository;


    @Autowired
    public FacilityReportDetailServiceImpl(FacilityReportDetailRepository facilityReportDetailRepository,
                                           FacilityReportRepository facilityReportRepository) {
        this.facilityReportDetailRepository = facilityReportDetailRepository;
        this.facilityReportRepository = facilityReportRepository;
    }


    @Override
    public Page<FacilityReportDetail> getFacilityReportDetailsByReportId(Long reportId, Pageable pageable) {
        return facilityReportDetailRepository.findByFacilityReportId(reportId, pageable);
    }

    @Override
    public FacilityReportDetail addHRComment(Long reportId, FacilityReportDetail detail) {
        // TODO: update once employee service not a blocker

        if (!facilityReportRepository.existsById(reportId)) {
            throw new ResourceNotFoundException("Facility Report with ID " + reportId + " not found");
        }

        Long hrEmployeeId = 1000L; // Hardcoded for now

        detail.setFacilityReportId(reportId);
        detail.setEmployeeId(hrEmployeeId);
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
}
