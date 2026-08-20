package com.salon.auth_user_service.service;

import com.salon.auth_user_service.dto.inDto.ChangePasswordRequestDto;
import com.salon.auth_user_service.dto.inDto.UpdateUserRequestDto;
import com.salon.auth_user_service.dto.inDto.UpdateUserStatusRequestDto;
import com.salon.auth_user_service.dto.outDto.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserResponseDto getCurrentUser(String email);
    UserResponseDto getUserById(Long id);
    Page<UserResponseDto> getAllUsers(Pageable pageable);
    UserResponseDto updateUser(Long id, UpdateUserRequestDto updateUserRequestDto);
    void updateUserStatus(Long id, UpdateUserStatusRequestDto request);
    void changePassword(String email, ChangePasswordRequestDto request);
}
