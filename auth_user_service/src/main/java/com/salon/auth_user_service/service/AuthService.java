package com.salon.auth_user_service.service;

import com.salon.auth_user_service.dto.inDto.LoginRequestDto;
import com.salon.auth_user_service.dto.inDto.RegisterRequestDto;
import com.salon.auth_user_service.dto.internalDto.LoginTokenResult;
import com.salon.auth_user_service.dto.outDto.LoginResponseDto;
import com.salon.auth_user_service.dto.outDto.RegisterResponseDto;

public interface AuthService {

    RegisterResponseDto register(RegisterRequestDto registerRequestDto);

    LoginTokenResult login(LoginRequestDto loginRequestDto);

    LoginTokenResult refresh(String refreshToken);

    void logout(String refreshToken);
}
