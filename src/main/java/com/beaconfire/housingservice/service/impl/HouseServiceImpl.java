package com.beaconfire.housingservice.service.impl;

import com.beaconfire.housingservice.dao.HouseRepository;
import com.beaconfire.housingservice.model.House;
import com.beaconfire.housingservice.service.HouseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {

    private final HouseRepository houseRepository;

    public HouseServiceImpl(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public House getAssignedHouseForEmployee(Long employeeId) {
        // TODO: implement logic to get assigned house for the employee
        return null;
    }

    @Override
    public List<House> getAllHouses() {
        // TODO: implement logic to return all houses
        return houseRepository.findAll();
    }

    @Override
    public House getHouseById(Long houseId) {
        // TODO: implement logic to get house by ID
        return houseRepository.findById(houseId).orElse(null);
    }

    @Override
    public House createHouse(House house) {
        // TODO: implement logic to create a new house
        return houseRepository.save(house);
    }

    @Override
    public void deleteHouse(Long houseId) {
        // TODO: implement logic to delete a house
        houseRepository.deleteById(houseId);
    }
}
