package com.beaconfire.housingservice.service;

import com.beaconfire.housingservice.model.FacilityReportDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FacilityReportDetailService {
    Page<FacilityReportDetail> getFacilityReportDetailsByReportId(Long reportId, Pageable pageable);
    FacilityReportDetail addHRComment(Long reportId, FacilityReportDetail detail);
    void updateHRComment(Long commentId, String newComment);
    FacilityReportDetail addEmployeeComment(Long reportId, FacilityReportDetail detail, String userId);
    String updateEmployeeComment(Long commentId, FacilityReportDetail newDetail);
    List<FacilityReportDetail> getAllCommentsForReport(Long reportId);
}
