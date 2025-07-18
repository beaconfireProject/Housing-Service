package com.beaconfire.housingservice.service.impl;

import com.beaconfire.housingservice.dao.FacilityRepository;
import com.beaconfire.housingservice.dao.HouseRepository;
import com.beaconfire.housingservice.dao.LandlordRepository;
import com.beaconfire.housingservice.dto.*;
import com.beaconfire.housingservice.exception.ResourceNotFoundException;
import com.beaconfire.housingservice.feign.EmployeeClient;
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
    private final EmployeeClient employeeClient;

    public HouseServiceImpl(HouseRepository houseRepository,
                            LandlordRepository landlordRepository,
                            FacilityRepository facilityRepository,
                            EmployeeClient employeeClient) {
        this.houseRepository = houseRepository;
        this.landlordRepository = landlordRepository;
        this.facilityRepository = facilityRepository;
        this.employeeClient = employeeClient;
    }

    @Override
    public AssignedHouseResponse getAssignedHouseForEmployee(String userId) {
        // Get employee info by id
        EmployeeResponse currentEmployee = employeeClient.getEmployeeByUserId(userId).getData();
        System.out.println("currentEmployee: " + currentEmployee);
        String houseIdStr = currentEmployee.getHouseId();
        System.out.println("houseId: " + houseIdStr);
        if (houseIdStr == null) return null;

        Long houseId;
        try {
            houseId = Long.parseLong(houseIdStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid houseId format: " + houseIdStr);
        }

        // Get house
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new ResourceNotFoundException("House not found with ID: " + houseId));

        // Get all employees in that house
        List<EmployeeResponse> occupants = employeeClient.getEmployeesByHouseId(houseId).getData();

        // Build occupant info list
        List<AssignedHouseResponse.OccupantInfo> occupantInfos = new ArrayList<>();
        for (EmployeeResponse emp : occupants) {
            occupantInfos.add(AssignedHouseResponse.OccupantInfo.builder()
                    .name(emp.getFirstName() + " " + emp.getLastName())
                    .phone(emp.getCellPhone())
                    .build());
        }

        return AssignedHouseResponse.builder()
                .houseId(houseId)
                .address(house.getAddress())
                .occupants(occupantInfos)
                .build();
    }

    @Override
    public List<Facility> getFacilitiesForAssignedHouse(String userId) {
        EmployeeResponse employee = employeeClient.getEmployeeByUserId(userId).getData();
        if (employee == null || employee.getHouseId() == null) {
            throw new ResourceNotFoundException("Employee has no assigned house.");
        }

        Long houseId;
        try {
            houseId = Long.parseLong(employee.getHouseId());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid house ID format: " + employee.getHouseId());
        }

        return facilityRepository.findByHouseId(houseId);
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

            int employeeCount = 0;
            try {
                EmployeeListResponseWrapper wrapper = employeeClient.getEmployeesByHouseId(house.getId());
                if (wrapper != null && wrapper.getData() != null) {
                    employeeCount = wrapper.getData().size();
                }
            } catch (Exception e) {
                // Optional: log error
                employeeCount = 0;
            }

            responses.add(HouseSummaryResponse.builder()
                    .houseId(house.getId())
                    .address(house.getAddress())
                    .numEmployees(employeeCount)
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

        List<EmployeeResponse> employeeList = new ArrayList<>();
        try {
            EmployeeListResponseWrapper wrapper = employeeClient.getEmployeesByHouseId(houseId);
            if (wrapper != null && wrapper.getData() != null) {
                employeeList = wrapper.getData();
            }
        } catch (Exception e) {
            // Optional: log error
            employeeList = new ArrayList<>();
        }

        List<HouseDetailsResponse.EmployeeSummary> employees = new ArrayList<>();
        for (EmployeeResponse emp : employeeList) {
            employees.add(HouseDetailsResponse.EmployeeSummary.builder()
                    .firstName(emp.getFirstName())
                    .lastName(emp.getLastName())
                    .email(emp.getEmail())
                    .phone(emp.getCellPhone())
                    .build());
        }
        int numEmployees = employees.size();


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

    @Override
    public void assignHouseToEmployee(String employeeId, Long houseId) {
        // Make sure house exists
        houseRepository.findById(houseId)
                .orElseThrow(() -> new ResourceNotFoundException("House not found"));

        // Construct minimal request body to patch
        Map<String, Object> patchBody = new HashMap<>();
        patchBody.put("houseId", houseId.toString());

        // Make PATCH request via Feign client
        employeeClient.patchEmployee(employeeId, patchBody);
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
