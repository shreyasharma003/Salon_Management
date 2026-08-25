package com.salon.attendanceservice.controller;

import com.salon.attendanceservice.dto.ApiResponse;
import com.salon.attendanceservice.dto.AttendanceRequest;
import com.salon.attendanceservice.dto.AttendanceResponse;
import com.salon.attendanceservice.enums.AttendanceStatus;
import com.salon.attendanceservice.service.AttendanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PostMapping
    public ResponseEntity<ApiResponse<AttendanceResponse>> createAttendance(@Valid @RequestBody AttendanceRequest request) {
        AttendanceResponse response = attendanceService.createAttendance(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Attendance created successfully", response));
    }

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getAttendanceByArtistId(@PathVariable Long artistId) {
        List<AttendanceResponse> responses = attendanceService.getAttendanceByArtistId(artistId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/date")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getAttendanceByDate(@RequestParam LocalDate date) {
        List<AttendanceResponse> responses = attendanceService.getAttendanceByDate(date);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getAllAttendance() {
        List<AttendanceResponse> responses = attendanceService.getAllAttendance();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getAttendanceByStatus(@PathVariable AttendanceStatus status) {
        List<AttendanceResponse> responses = attendanceService.getAttendanceByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PutMapping("/{attendanceId}/status")
    public ResponseEntity<ApiResponse<Void>> updateAttendanceStatus(
            @PathVariable Long attendanceId,
            @RequestParam AttendanceStatus status) {
        attendanceService.updateAttendanceStatus(attendanceId, status);
        return ResponseEntity.ok(ApiResponse.success("Attendance status updated successfully", null));
    }

    @GetMapping("/present/{artistId}")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getPresentByArtistId(@PathVariable Long artistId) {
        List<AttendanceResponse> responses = attendanceService.getAttendanceByArtistIdAndStatus(artistId, AttendanceStatus.PRESENT);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/absent/{artistId}")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getAbsentByArtistId(@PathVariable Long artistId) {
        List<AttendanceResponse> responses = attendanceService.getAttendanceByArtistIdAndStatus(artistId, AttendanceStatus.ABSENT);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/half-day/{artistId}")
    public ResponseEntity<ApiResponse<List<AttendanceResponse>>> getHalfDayByArtistId(@PathVariable Long artistId) {
        List<AttendanceResponse> responses = attendanceService.getAttendanceByArtistIdAndStatus(artistId, AttendanceStatus.HALF_DAY);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PostMapping("/mark")
    public ResponseEntity<ApiResponse<AttendanceResponse>> markAttendance(@Valid @RequestBody AttendanceRequest request) {
        AttendanceResponse response = attendanceService.createAttendance(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created("Attendance marked successfully", response));
    }
}
