package com.beaconfire.housingservice.dao;

import com.beaconfire.housingservice.model.FacilityReportDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilityReportDetailRepository extends JpaRepository<FacilityReportDetail, Long> {
    List<FacilityReportDetail> findByFacilityReportIdOrderByCreateDateDesc(Long reportId);
    List<FacilityReportDetail> findByFacilityReportId(Long reportId);
}
