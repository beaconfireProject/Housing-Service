package com.beaconfire.housingservice.service.impl;

import com.beaconfire.housingservice.dao.LandlordRepository;
import com.beaconfire.housingservice.model.Landlord;
import com.beaconfire.housingservice.service.LandlordService;
import org.springframework.stereotype.Service;

@Service
public class LandlordServiceImpl implements LandlordService {

    private final LandlordRepository landlordRepository;

    public LandlordServiceImpl(LandlordRepository landlordRepository) {
        this.landlordRepository = landlordRepository;
    }

    @Override
    public Landlord getLandlordById(Long landlordId) {
        // TODO: implement logic to get landlord by ID
        return landlordRepository.findById(landlordId).orElse(null);
    }
}
