package com.beaconfire.housingservice.service.impl;

import com.beaconfire.housingservice.dao.FacilityRepository;
import com.beaconfire.housingservice.model.Facility;
import com.beaconfire.housingservice.service.FacilityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService {

    private final FacilityRepository facilityRepository;

    public FacilityServiceImpl(FacilityRepository facilityRepository) {
        this.facilityRepository = facilityRepository;
    }

    @Override
    public List<Facility> getFacilitiesByHouseId(Long houseId) {
        // TODO: implement logic to get facilities for a house
        return facilityRepository.findByHouseId(houseId);
    }
}
