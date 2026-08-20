package com.salon.auth_user_service.mapper;

import com.salon.auth_user_service.dto.inDto.RegisterRequestDto;
import com.salon.auth_user_service.dto.outDto.UserResponseDto;
import com.salon.auth_user_service.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto toDto(User user);
    User toEntity(RegisterRequestDto dto);

}
