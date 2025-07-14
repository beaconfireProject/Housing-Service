package com.beaconfire.housingservice.service.impl;

import com.beaconfire.housingservice.dao.FacilityRepository;
import com.beaconfire.housingservice.dao.HouseRepository;
import com.beaconfire.housingservice.dao.LandlordRepository;
import com.beaconfire.housingservice.dto.HouseDetailsResponse;
import com.beaconfire.housingservice.dto.HouseSummaryResponse;
import com.beaconfire.housingservice.exception.ResourceNotFoundException;
import com.beaconfire.housingservice.model.Facility;
import com.beaconfire.housingservice.model.House;
import com.beaconfire.housingservice.model.Landlord;
import com.beaconfire.housingservice.service.HouseService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;
    private final LandlordRepository landlordRepository;
    private final FacilityRepository facilityRepository;

    public HouseServiceImpl(HouseRepository houseRepository,
                            LandlordRepository landlordRepository,
                            FacilityRepository facilityRepository) {
        this.houseRepository = houseRepository;
        this.landlordRepository = landlordRepository;
        this.facilityRepository = facilityRepository;
    }

    @Override
    public House getAssignedHouseForEmployee(Long employeeId) {
        // Hardcoded mapping for now
        if (employeeId == 15L) {
            House house = houseRepository.findById(2L).orElse(null);
            System.out.println("Fetched House: " + house);
            return house;
        }
        return null;
    }


    // HR Services

    @Override
    public List<HouseSummaryResponse> getAllHousesSummary() {
        List<House> houses = houseRepository.findAll();
        List<HouseSummaryResponse> responses = new ArrayList<>();

        for (House house : houses) {
            Optional<Landlord> optionalLandlord = landlordRepository.findById(house.getLandlordId());
            if (!optionalLandlord.isPresent()) continue;

            Landlord landlord = optionalLandlord.get();

            responses.add(HouseSummaryResponse.builder()
                    .houseId(house.getId())
                    .address(house.getAddress())
                    // TODO: change later when employee service not a block
                    .numEmployees(0) // mock
                    .landlord(HouseSummaryResponse.LandlordInfo.builder()
                            .firstName(landlord.getFirstName())
                            .lastName(landlord.getLastName())
                            .phone(landlord.getCellPhone())
                            .email(landlord.getEmail())
                            .build())
                    .build());
        }

        return responses;
    }

    @Override
    public HouseDetailsResponse getHouseDetailsById(Long houseId) {
        House house = houseRepository.findById(houseId).orElse(null);
        if (house == null) return null;

        // landlord info
        Optional<Landlord> optionalLandlord = landlordRepository.findById(house.getLandlordId());
        if (!optionalLandlord.isPresent()) return null;
        Landlord landlord = optionalLandlord.get();

        // employees = mock empty list for now
        // TODO: change later when employee service not a block
        List<HouseDetailsResponse.EmployeeSummary> employees = new ArrayList<>();
        int numEmployees = 0;

        // facilities
        List<Facility> facilities = facilityRepository.findByHouseId(houseId);

        Map<String, Integer> facilityCounts = new HashMap<>();
        for (Facility facility : facilities) {
            facilityCounts.put(facility.getType().toLowerCase() + "s", facility.getQuantity()); // plural keys: beds, tables...
        }

        return HouseDetailsResponse.builder()
                .houseId(house.getId())
                .address(house.getAddress())
                .landlord(HouseDetailsResponse.LandlordInfo.builder()
                        .firstName(landlord.getFirstName())
                        .lastName(landlord.getLastName())
                        .phone(landlord.getCellPhone())
                        .email(landlord.getEmail())
                        .build())
                .numEmployees(numEmployees)
                .employees(employees)
                .facilities(facilityCounts)
                .build();
    }




    // Basic CRUD

    @Override
    public List<House> getAllHouses() {
        return houseRepository.findAll();
    }

    @Override
    public House getHouseById(Long houseId) {
        return houseRepository.findById(houseId).orElse(null);
    }

    @Override
    public House createHouse(House house) {
        return houseRepository.save(house);
    }

    @Override
    public void deleteHouse(Long houseId) {
        if (!houseRepository.existsById(houseId)) {
            throw new ResourceNotFoundException("House not found with ID: " + houseId);
        }
        houseRepository.deleteById(houseId);
    }
}
