package com.beaconfire.housingservice.controller;

import com.beaconfire.housingservice.dto.HouseDetailsResponse;
import com.beaconfire.housingservice.dto.HouseSummaryResponse;
import com.beaconfire.housingservice.service.*;
import com.beaconfire.housingservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @ResponseBody
    public House getAssignedHouse() {
        // TODO: current blocked by employee service
        Long fakeAuthenticatedEmployeeId = 15L;
        System.out.println("reached");
        return houseService.getAssignedHouseForEmployee(fakeAuthenticatedEmployeeId);
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
    public ResponseEntity<List<HouseSummaryResponse>> getAllHouses() {
        return ResponseEntity.ok(houseService.getAllHousesSummary());
    }

    // 7. Get House Details by ID (HR)
    @GetMapping("/{houseId}")
    public ResponseEntity<HouseDetailsResponse> getHouseDetails(@PathVariable Long houseId) {
        return ResponseEntity.ok(houseService.getHouseDetailsById(houseId));
    }

    // 8. Create New House (HR)
    @PostMapping
    public ResponseEntity<Map<String, Object>> createHouse(@Valid @RequestBody House house) {
        House savedHouse = houseService.createHouse(house);

        Map<String, Object> response = new HashMap<>();
        response.put("houseId", savedHouse.getId());
        response.put("message", "House created successfully");

        return ResponseEntity.ok(response);
    }

    // 9. Delete House (HR)
    @DeleteMapping("/{houseId}")
    public ResponseEntity<Map<String, String>> deleteHouse(@PathVariable Long houseId) {
        houseService.deleteHouse(houseId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "House deleted successfully");

        return ResponseEntity.ok(response);
    }

    // 10. Get Facility Reports for House (HR)
    @GetMapping("/{houseId}/facility-reports")
    public List<FacilityReport> getFacilityReportsForHouse(@PathVariable Long houseId) {
        return facilityReportService.getFacilityReportsByHouseId(houseId);
    }

    // 11. HR Add Comment to Report
    @PostMapping("/facility-reports/{reportId}/comments")
    public FacilityReportDetail addHRComment(@Valid @PathVariable Integer reportId,
                                             @RequestBody FacilityReportDetail detail) {
        return facilityReportDetailService.addHRComment(reportId.longValue(), detail);
    }

    // 12. HR Update Comment
    @PutMapping("/facility-reports/comments/{commentId}")
    public ResponseEntity<Map<String, String>> updateHRComment(
            @PathVariable Integer commentId,
            @RequestBody Map<String, String> body) {

        String newComment = body.get("description");

        facilityReportDetailService.updateHRComment(commentId.longValue(), newComment);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Comment updated");
        return ResponseEntity.ok(response);
    }

    // 13. Get Single Report (with pagination later)
    @GetMapping("/report/{reportId}")
    public Page<FacilityReportDetail> getFacilityReportDetailsByReportId(
            @PathVariable Integer reportId,
            Pageable pageable) {
        return facilityReportDetailService.getFacilityReportDetailsByReportId(reportId.longValue(), pageable);
    }
}
