package com.beaconfire.housingservice.service;

import com.beaconfire.housingservice.model.House;

import java.util.List;

public interface HouseService {
    House getAssignedHouseForEmployee(Long employeeId);
    List<House> getAllHouses();
    House getHouseById(Long houseId);
    House createHouse(House house);
    void deleteHouse(Long houseId);
}
