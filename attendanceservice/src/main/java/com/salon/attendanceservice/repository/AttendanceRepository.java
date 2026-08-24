package com.salon.attendanceservice.repository;

import com.salon.attendanceservice.entity.Attendance;
import com.salon.attendanceservice.enums.AttendanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByArtistId(Long artistId);

    List<Attendance> findByDate(LocalDate date);

    List<Attendance> findByStatus(AttendanceStatus status);

    List<Attendance> findByArtistIdAndStatus(Long artistId, AttendanceStatus status);

}
