package com.beaconfire.housingservice.dao;

import com.beaconfire.housingservice.model.FacilityReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilityReportRepository extends JpaRepository<FacilityReport, Long> {
    List<FacilityReport> findByFacility_HouseId(Long houseId);

    @Query(
            value = "SELECT fr.* " +
                    "FROM HR_Portal.FacilityReport fr " +
                    "JOIN HR_Portal.Facility f ON fr.FacilityID = f.ID " +
                    "WHERE f.HouseID = :houseId " +
                    "ORDER BY fr.CreateDate DESC",
            countQuery = "SELECT COUNT(*) " +
                    "FROM HR_Portal.FacilityReport fr " +
                    "JOIN HR_Portal.Facility f ON fr.FacilityID = f.ID " +
                    "WHERE f.HouseID = :houseId",
            nativeQuery = true
    )
    Page<FacilityReport> findByHouseIdNative(@Param("houseId") Long houseId, Pageable pageable);




}
