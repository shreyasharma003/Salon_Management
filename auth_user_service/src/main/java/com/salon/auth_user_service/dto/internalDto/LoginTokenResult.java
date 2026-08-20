package com.salon.auth_user_service.dto.internalDto;

import com.salon.auth_user_service.dto.outDto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginTokenResult {

    private String accessToken;

    private String refreshToken;

    private UserResponseDto user;

}
