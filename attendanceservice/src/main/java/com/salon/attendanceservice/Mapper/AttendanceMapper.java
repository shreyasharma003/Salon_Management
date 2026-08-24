package com.salon.attendanceservice.mapper;

import com.salon.attendanceservice.dto.ArtistResponseDto;
import com.salon.attendanceservice.dto.AttendanceRequest;
import com.salon.attendanceservice.dto.AttendanceResponse;
import com.salon.attendanceservice.entity.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface AttendanceMapper {


    Attendance toEntity(AttendanceRequest dto);

    AttendanceResponse toResponse(Attendance attendance);

    @Mapping(target = "id", source = "attendance.id")
    @Mapping(target = "artistId", source = "attendance.artistId")
    @Mapping(target = "date", source = "attendance.date")
    @Mapping(target = "status", source = "attendance.status")
    @Mapping(target = "name", source = "artist.name")
    @Mapping(target = "email", source = "artist.email")
    @Mapping(target = "contactNumber", source = "artist.contactNumber")
    @Mapping(target = "specialization", source = "artist.specialization")
    @Mapping(target = "available", source = "artist.available")
    AttendanceResponse toResponseDto(
            Attendance attendance,
            ArtistResponseDto artist
    );
}
