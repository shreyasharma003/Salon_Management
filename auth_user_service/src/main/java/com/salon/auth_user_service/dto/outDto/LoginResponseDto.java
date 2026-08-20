package com.salon.auth_user_service.dto.outDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class LoginResponseDto {

    private String message;
    private String accessToken;
    private UserResponseDto user;
}
