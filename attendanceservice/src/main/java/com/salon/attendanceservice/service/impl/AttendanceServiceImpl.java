package com.salon.attendanceservice.service.impl;

import com.salon.attendanceservice.dto.ArtistResponseDto;
import com.salon.attendanceservice.dto.AttendanceRequest;
import com.salon.attendanceservice.dto.AttendanceResponse;
import com.salon.attendanceservice.entity.Attendance;
import com.salon.attendanceservice.enums.AttendanceStatus;
import com.salon.attendanceservice.mapper.AttendanceMapper;
import com.salon.attendanceservice.repository.AttendanceRepository;
import com.salon.attendanceservice.service.ArtistClient;
import com.salon.attendanceservice.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final AttendanceMapper attendanceMapper;

    private final ArtistClient artistClient;

    public AttendanceResponse createAttendance(AttendanceRequest request) {

        //1. Get artist details from artist service
        ArtistResponseDto artist = artistClient.getArtistById(request.getArtistId());

        //2. Create attendance entity
        Attendance attendance = Attendance.builder()
                .artistId(request.getArtistId())
                .date(request.getDate())
                .status(request.getStatus())
                .build();

        //3. Save attendance
        Attendance saved = attendanceRepository.save(attendance);

        //4. Return response with artist details
        return attendanceMapper.toResponseDto(saved, artist);

    }

    @Override
    public List<AttendanceResponse> getAttendanceByArtistId(Long artistId) {
        List<Attendance> attendances = attendanceRepository.findByArtistId(artistId);
        ArtistResponseDto artist = artistClient.getArtistById(artistId);
        return attendances.stream()
                .map(attendance -> attendanceMapper.toResponseDto(attendance, artist))
                .toList();
    }

    @Override
    public List<AttendanceResponse> getAllAttendance() {
        List<Attendance> attendances = attendanceRepository.findAll();
        return attendances.stream()
                .map(attendance -> {
                    ArtistResponseDto artist = artistClient.getArtistById(attendance.getArtistId());
                    return attendanceMapper.toResponseDto(attendance, artist);
                })
                .toList();
    }

    @Override
    public List<AttendanceResponse> getAttendanceByArtistIdAndStatus(Long artistId, AttendanceStatus status) {
        List<Attendance> attendances = attendanceRepository.findByArtistIdAndStatus(artistId, status);
        ArtistResponseDto artist = artistClient.getArtistById(artistId);
        return attendances.stream()
                .map(attendance -> attendanceMapper.toResponseDto(attendance, artist))
                .toList();
    }

    @Override
    public List<AttendanceResponse> getAttendanceByDate(LocalDate date) {
        List<Attendance> attendances = attendanceRepository.findByDate(date);
        return attendances.stream()
                .map(attendance -> {
                    ArtistResponseDto artist = artistClient.getArtistById(attendance.getArtistId());
                    return attendanceMapper.toResponseDto(attendance, artist);
                })
                .toList();
    }

    @Override
    public List<AttendanceResponse> getAttendanceByStatus(AttendanceStatus status) {
        List<Attendance> attendances = attendanceRepository.findByStatus(status);
        return attendances.stream()
                .map(attendance -> {
                    ArtistResponseDto artist = artistClient.getArtistById(attendance.getArtistId());
                    return attendanceMapper.toResponseDto(attendance, artist);
                })
                .toList();
    }

    @Override
    public void updateAttendanceStatus(Long attendanceId, AttendanceStatus status) {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance not found"));
        attendance.setStatus(status);
        attendanceRepository.save(attendance);
    }

    @Override
    public AttendanceResponse markPresent(Long artistId) {
        Attendance attendance = Attendance.builder()
                .artistId(artistId)
                .date(LocalDate.now())
                .status(AttendanceStatus.PRESENT)
                .build();
        Attendance saved = attendanceRepository.save(attendance);
        return attendanceMapper.toResponse(saved);
    }

    @Override
    public AttendanceResponse markAbsent(Long artistId) {
        Attendance attendance = Attendance.builder()
                .artistId(artistId)
                .date(LocalDate.now())
                .status(AttendanceStatus.ABSENT)
                .build();
        Attendance saved = attendanceRepository.save(attendance);
        return attendanceMapper.toResponse(saved);
    }

    @Override
    public AttendanceResponse markHalfDay(Long artistId) {
        Attendance attendance = Attendance.builder()
                .artistId(artistId)
                .date(LocalDate.now())
                .status(AttendanceStatus.HALF_DAY)
                .build();
        Attendance saved = attendanceRepository.save(attendance);
        return attendanceMapper.toResponse(saved);
    }

}