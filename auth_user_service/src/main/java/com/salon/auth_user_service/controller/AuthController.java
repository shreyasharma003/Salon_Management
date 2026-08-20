package com.salon.auth_user_service.controller;

import com.salon.auth_user_service.dto.inDto.LoginRequestDto;
import com.salon.auth_user_service.dto.inDto.RegisterRequestDto;
import com.salon.auth_user_service.dto.internalDto.LoginTokenResult;
import com.salon.auth_user_service.dto.outDto.LoginResponseDto;
import com.salon.auth_user_service.dto.outDto.RegisterResponseDto;
import com.salon.auth_user_service.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpire;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(@Valid @RequestBody RegisterRequestDto registerRequestDto) {
        RegisterResponseDto registerResponseDto = authService.register(registerRequestDto);
        return  ResponseEntity.status(HttpStatus.OK).body(registerResponseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto request) {

        LoginTokenResult result =
                authService.login(request);


        ResponseCookie refreshCookie = createRefreshTokenCookie(result.getRefreshToken());


        LoginResponseDto response =
                LoginResponseDto.builder()
                        .message("Login successfully")
                        .accessToken(result.getAccessToken())
                        .user(result.getUser())
                        .build();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.SET_COOKIE,
                        refreshCookie.toString()
                )
                .body(response);
    }

    @PostMapping("/refresh")
    public  ResponseEntity<LoginResponseDto> refreshToken(
            @CookieValue(name = "refresh_token" , required = false)
            String refreshToken) {
        LoginTokenResult loginTokenResult = authService.refresh(refreshToken);

        ResponseCookie refreshTokenCookie = createRefreshTokenCookie(loginTokenResult.getRefreshToken());

        LoginResponseDto loginResponseDto = LoginResponseDto.builder()
                .message("Toekn Refreshed Successfully")
                .accessToken(loginTokenResult.getAccessToken())
                .user(loginTokenResult.getUser())
                .build();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.SET_COOKIE,
                        refreshTokenCookie.toString()
                )
                .body(loginResponseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(name = "refresh_token" , required = false)
            String refreshToken
    ){
        authService.logout(refreshToken);

        ResponseCookie clearCookie = createClearRefreshTokenCookie();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.SET_COOKIE,
                        clearCookie.toString()
                )
                .build();
    }

    private ResponseCookie createRefreshTokenCookie(String refreshToken){
        return ResponseCookie.from(
                        "refresh_token",
                        refreshToken
                )
                .httpOnly(true)
                .secure(false) // for production should be true.
                .sameSite("Strict")
                .path("/auth")
                .maxAge(Duration.ofMillis(refreshTokenExpire))
                .build();
    }

    private ResponseCookie createClearRefreshTokenCookie() {

        return ResponseCookie.from(
                        "refresh_token",
                        ""
                )
                .httpOnly(true)
                .secure(false) // true in production
                .sameSite("Strict")
                .path("/auth")
                .maxAge(Duration.ZERO)
                .build();
    }
}
