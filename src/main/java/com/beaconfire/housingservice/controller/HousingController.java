package com.beaconfire.housingservice.controller;

import com.beaconfire.housingservice.service.*;
import com.beaconfire.housingservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/housing")
public class HousingController {

    private final HouseService houseService;
    private final FacilityReportService facilityReportService;
    private final FacilityReportDetailService facilityReportDetailService;

    @Autowired
    public HousingController(
            HouseService houseService,
            FacilityReportService facilityReportService,
            FacilityReportDetailService facilityReportDetailService
    ) {
        this.houseService = houseService;
        this.facilityReportService = facilityReportService;
        this.facilityReportDetailService = facilityReportDetailService;
    }

    // 1. Get Assigned House (Employee)
    @GetMapping("/assigned")
    public House getAssignedHouse() {
        // TODO: call houseService.getAssignedHouseForEmployee(authenticatedId)
        return null;
    }

    // 2. Submit Facility Report (Employee)
    @PostMapping("/facility-report")
    public FacilityReport submitFacilityReport(@RequestBody FacilityReport report) {
        // TODO: call facilityReportService.createFacilityReportForEmployee(report)
        return null;
    }

    // 3. Get Facility Reports (Employee)
    @GetMapping("/facility-report")
    public List<FacilityReport> getFacilityReportsForEmployee() {
        // TODO: call facilityReportService.getReportsByEmployeeHouse(employeeId)
        return null;
    }

    // 4. Add Comment to Facility Report (Employee)
    @PostMapping("/facility-report/{reportId}/comment")
    public FacilityReportDetail addEmployeeComment(@PathVariable Integer reportId, @RequestBody FacilityReportDetail detail) {
        // TODO: facilityReportDetailService.addEmployeeComment(reportId, detail)
        return null;
    }

    // 5. Update Own Comment (Employee)
    @PutMapping("/facility-report/comment/{commentId}")
    public String updateEmployeeComment(@PathVariable Integer commentId, @RequestBody FacilityReportDetail detail) {
        // TODO: facilityReportDetailService.updateEmployeeComment(commentId, detail)
        return null;
    }

    // 6. Get All Houses (HR)
    @GetMapping
    public List<House> getAllHouses() {
        // TODO: houseService.getAllHouses()
        return null;
    }

    // 7. Get House Details by ID (HR)
    @GetMapping("/{houseId}")
    public House getHouseDetails(@PathVariable Long houseId) {
        // TODO: houseService.getHouseById(houseId)
        return null;
    }

    // 8. Create New House (HR)
    @PostMapping
    public House createHouse(@RequestBody House house) {
        // TODO: houseService.createHouse(house)
        return null;
    }

    // 9. Delete House (HR)
    @DeleteMapping("/{houseId}")
    public String deleteHouse(@PathVariable Long houseId) {
        // TODO: houseService.deleteHouse(houseId)
        return null;
    }

    // 10. Get Facility Reports for House (HR)
    @GetMapping("/{houseId}/facility-reports")
    public List<FacilityReport> getFacilityReportsForHouse(@PathVariable Long houseId) {
        // TODO: facilityReportService.getReportsByHouse(houseId)
        return null;
    }

    // 11. HR Add Comment to Report
    @PostMapping("/facility-reports/{reportId}/comments")
    public FacilityReportDetail addHRComment(@PathVariable Integer reportId, @RequestBody FacilityReportDetail detail) {
        // TODO: facilityReportDetailService.addHRComment(reportId, detail)
        return null;
    }

    // 12. HR Update Comment
    @PutMapping("/facility-reports/comments/{commentId}")
    public String updateHRComment(@PathVariable Integer commentId, @RequestBody FacilityReportDetail detail) {
        // TODO: facilityReportDetailService.updateHRComment(commentId, detail)
        return null;
    }

    // 13. Get Single Report (with pagination later)
    @GetMapping("/report/{reportId}")
    public FacilityReport getFacilityReportById(@PathVariable Integer reportId) {
        // TODO: facilityReportService.getReportById(reportId)
        return null;
    }
}
