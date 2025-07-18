package com.beaconfire.housingservice.controller;

import com.beaconfire.housingservice.dto.*;
import com.beaconfire.housingservice.security.JwtUtil;
import com.beaconfire.housingservice.service.*;
import com.beaconfire.housingservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/housing")
public class HousingController {

    private final HouseService houseService;
    private final FacilityReportService facilityReportService;
    private final FacilityReportDetailService facilityReportDetailService;
    private final JwtUtil jwtUtil;

    @Autowired
    public HousingController(
            HouseService houseService,
            FacilityReportService facilityReportService,
            FacilityReportDetailService facilityReportDetailService,
            JwtUtil jwtUtil){
        this.houseService = houseService;
        this.facilityReportService = facilityReportService;
        this.facilityReportDetailService = facilityReportDetailService;
        this.jwtUtil = jwtUtil;
    }

    // 1. Get Assigned House (Employee)
    @GetMapping("/assigned")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public AssignedHouseResponse getAssignedHouse(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        String token = authHeader.substring(7);
        String userId = jwtUtil.extractUserId(token);
        System.out.println("CURRENT USER ID: " + userId);
        return houseService.getAssignedHouseForEmployee(userId);
    }

    // 2. Submit Facility Report (Employee)
    @PostMapping("/facility-report")
    public FacilityReport submitFacilityReport(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody FacilityReportRequest request) {

        String token = authHeader.substring(7);
        String userId = jwtUtil.extractUserId(token);

        return facilityReportService.submitFacilityReport(userId, request);
    }

    // 3. Get Facility Reports (Employee)
    @GetMapping("/facility-report")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public List<FacilityReport> getFacilityReportsForEmployee(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        String token = authHeader.substring(7);
        String userId = jwtUtil.extractUserId(token);

        return facilityReportService.getFacilityReportsForEmployeeHouse(userId);
    }

    // 4. Add Comment to Facility Report (Employee)
    @PostMapping("/facility-report/{reportId}/comment")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Map<String, String>> addEmployeeComment(
            @PathVariable Long reportId,
            @RequestBody FacilityReportDetail detail,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        String token = authHeader.substring(7);
        String userId = jwtUtil.extractUserId(token);

        facilityReportDetailService.addEmployeeComment(reportId, detail, userId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Comment added successfully");
        return ResponseEntity.ok(response);
    }

    // 5. Update Own Comment (Employee)
    @PutMapping("/facility-report/comment/{commentId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Map<String, String>> updateEmployeeComment(
            @PathVariable Long commentId,
            @RequestBody FacilityReportDetail detail) {

        String result = facilityReportDetailService.updateEmployeeComment(commentId, detail);

        Map<String, String> response = new HashMap<>();
        response.put("message", result);

        return ResponseEntity.ok(response);
    }

    // 6. Get All Houses (HR)
    @PreAuthorize("hasRole('HR')")
    @GetMapping
    public ResponseEntity<List<HouseSummaryResponse>> getAllHouses() {
        return ResponseEntity.ok(houseService.getAllHousesSummary());
    }

    // 7. Get House Details by ID (HR)
    @PreAuthorize("hasRole('HR')")
    @GetMapping("/{houseId}")
    public ResponseEntity<HouseDetailsResponse> getHouseDetails(@PathVariable Long houseId) {
        return ResponseEntity.ok(houseService.getHouseDetailsById(houseId));
    }

    // 8. Create New House (HR)
    @PostMapping
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Map<String, Object>> createHouse(@Valid @RequestBody House house) {
        House savedHouse = houseService.createHouse(house);

        Map<String, Object> response = new HashMap<>();
        response.put("houseId", savedHouse.getId());
        response.put("message", "House created successfully");

        return ResponseEntity.ok(response);
    }

    // 9. Delete House (HR)
    @DeleteMapping("/{houseId}")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<Map<String, String>> deleteHouse(@PathVariable Long houseId) {
        houseService.deleteHouse(houseId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "House deleted successfully");

        return ResponseEntity.ok(response);
    }

    // 10. Get Facility Reports for House (HR)
    @GetMapping("/{houseId}/facility-reports")
    @PreAuthorize("hasRole('HR')")
    public List<FacilityReport> getFacilityReportsForHouse(@PathVariable Long houseId) {
        return facilityReportService.getFacilityReportsByHouseId(houseId);
    }

    // 11. HR Add Comment to Report
    @PostMapping("/facility-reports/{reportId}/comments")
    @PreAuthorize("hasRole('HR')")
    public FacilityReportDetail addHRComment(@Valid @PathVariable Integer reportId,
                                             @RequestBody FacilityReportDetail detail) {
        return facilityReportDetailService.addHRComment(reportId.longValue(), detail);
    }

    // 12. HR Update Comment
    @PutMapping("/facility-reports/comments/{commentId}")
    @PreAuthorize("hasRole('HR')")
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
    @PreAuthorize("hasRole('HR')")
    public Page<FacilityReportDetail> getFacilityReportDetailsByReportId(
            @PathVariable Integer reportId,
            Pageable pageable) {
        return facilityReportDetailService.getFacilityReportDetailsByReportId(reportId.longValue(), pageable);
    }

    // 14. Assign House to Employee (HR)
    @PatchMapping("/assign-house")
    @PreAuthorize("hasRole('HR')")
    public ResponseEntity<String> assignHouseToEmployee(@RequestBody AssignHouseRequest request) {
        houseService.assignHouseToEmployee(request.getEmployeeId(), request.getHouseId());
        return ResponseEntity.ok("House assigned to employee successfully.");
    }

    // 15. Get List of Facilities
    @GetMapping("/facilities")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<Facility>> getFacilitiesForAssignedHouse(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {

        String token = authHeader.substring(7);
        String userId = jwtUtil.extractUserId(token);
        List<Facility> facilities = houseService.getFacilitiesForAssignedHouse(userId);
        return ResponseEntity.ok(facilities);
    }

    @GetMapping("/facility-report/{reportId}/comments")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public List<FacilityReportDetail> getCommentsForFacilityReport(@PathVariable Long reportId) {
        return facilityReportDetailService.getAllCommentsForReport(reportId);
    }

}
