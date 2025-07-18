package com.beaconfire.housingservice.service;

import com.beaconfire.housingservice.dto.AssignedHouseResponse;
import com.beaconfire.housingservice.dto.HouseDetailsResponse;
import com.beaconfire.housingservice.dto.HouseSummaryResponse;
import com.beaconfire.housingservice.model.Facility;
import com.beaconfire.housingservice.model.House;

import java.util.List;

public interface HouseService {
    AssignedHouseResponse getAssignedHouseForEmployee(String userId);
    List<House> getAllHouses();
    List<Facility> getFacilitiesForAssignedHouse(String userId);

    // HR Endpoints

    List<HouseSummaryResponse> getAllHousesSummary();
    HouseDetailsResponse getHouseDetailsById(Long houseId);

    // Basic Crud

    House getHouseById(Long houseId);
    House createHouse(House house);
    void deleteHouse(Long houseId);
    void assignHouseToEmployee(String employeeId, Long houseId);
}
