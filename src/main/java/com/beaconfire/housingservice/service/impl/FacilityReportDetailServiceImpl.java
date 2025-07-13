package com.beaconfire.housingservice.service.impl;

import com.beaconfire.housingservice.dao.FacilityReportDetailRepository;
import com.beaconfire.housingservice.model.FacilityReportDetail;
import com.beaconfire.housingservice.service.FacilityReportDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityReportDetailServiceImpl implements FacilityReportDetailService {

    private final FacilityReportDetailRepository facilityReportDetailRepository;

    @Autowired
    public FacilityReportDetailServiceImpl(FacilityReportDetailRepository facilityReportDetailRepository) {
        this.facilityReportDetailRepository = facilityReportDetailRepository;
    }

    @Override
    public FacilityReportDetail getFacilityReportDetailById(Long id) {
        return facilityReportDetailRepository.findById(id).orElse(null);
    }

    @Override
    public List<FacilityReportDetail> getFacilityReportDetailsByReportId(Long reportId) {
        return facilityReportDetailRepository.findByFacilityReportId(reportId);
    }

    @Override
    public FacilityReportDetail saveFacilityReportDetail(FacilityReportDetail detail) {
        return facilityReportDetailRepository.save(detail);
    }
}
