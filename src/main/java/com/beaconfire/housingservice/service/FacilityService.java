package com.beaconfire.housingservice.service;

import com.beaconfire.housingservice.model.Facility;

import java.util.List;

public interface FacilityService {
    List<Facility> getFacilitiesByHouseId(Long houseId);
}
