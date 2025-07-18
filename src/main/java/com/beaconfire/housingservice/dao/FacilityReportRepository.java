package com.beaconfire.housingservice.dao;

import com.beaconfire.housingservice.model.FacilityReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilityReportRepository extends JpaRepository<FacilityReport, Long> {
    List<FacilityReport> findByFacility_HouseId(Long houseId);
}
