package com.salon.attendanceservice.dto;

import com.salon.attendanceservice.enums.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponse {

    private Long id;
    private Long artistId;
    private String name;
    private String email;
    private String contactNumber;
    private String specialization;
    private boolean available;
    private LocalDate date;
    private AttendanceStatus status;
}
