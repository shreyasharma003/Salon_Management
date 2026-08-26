package com.salon.attendanceservice.service;

import com.salon.attendanceservice.dto.AttendanceRequest;
import com.salon.attendanceservice.dto.AttendanceResponse;
import com.salon.attendanceservice.enums.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AttendanceService {

    AttendanceResponse createAttendance(AttendanceRequest request);

    List<AttendanceResponse> getAttendanceByArtistId(Long artistId);

    List<AttendanceResponse> getAllAttendance();

    AttendanceResponse getAttendanceByArtistIdAndDate(Long artistId, LocalDate date);

    List<AttendanceResponse> getAttendanceByArtistIdAndStatus(Long artistId, AttendanceStatus status);

    List<AttendanceResponse> getAttendanceByDate(LocalDate date);

    List<AttendanceResponse> getAttendanceByStatus(AttendanceStatus status);

    void updateAttendanceStatus(Long attendanceId, AttendanceStatus status);

    AttendanceResponse markPresent(
            Long artistId
    );

    AttendanceResponse markAbsent(Long artistId);

    AttendanceResponse markHalfDay(
            Long artistId
    );
}
