package com.beaconfire.housingservice.service;

import com.beaconfire.housingservice.model.FacilityReportDetail;

import java.util.List;

public interface FacilityReportDetailService {
    FacilityReportDetail getFacilityReportDetailById(Long id);
    List<FacilityReportDetail> getFacilityReportDetailsByReportId(Long reportId);
    FacilityReportDetail saveFacilityReportDetail(FacilityReportDetail detail);
}
