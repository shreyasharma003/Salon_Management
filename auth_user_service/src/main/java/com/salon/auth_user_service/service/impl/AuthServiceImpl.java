    package com.salon.auth_user_service.service.impl;

    import com.salon.auth_user_service.dto.inDto.LoginRequestDto;
    import com.salon.auth_user_service.dto.inDto.RegisterRequestDto;
    import com.salon.auth_user_service.dto.internalDto.LoginTokenResult;
    import com.salon.auth_user_service.dto.outDto.RegisterResponseDto;
    import com.salon.auth_user_service.dto.outDto.UserResponseDto;
    import com.salon.auth_user_service.exception.InvalidCredentialsException;
    import com.salon.auth_user_service.exception.InvalidRefreshTokenException;
    import com.salon.auth_user_service.exception.UserAlreadyExistsException;
    import com.salon.auth_user_service.mapper.UserMapper;
    import com.salon.auth_user_service.model.RefreshToken;
    import com.salon.auth_user_service.model.Role;
    import com.salon.auth_user_service.model.User;
    import com.salon.auth_user_service.repository.RefreshTokenRepository;
    import com.salon.auth_user_service.repository.UserRepository;
    import com.salon.auth_user_service.security.CustomUserDetailsService;
    import com.salon.auth_user_service.security.JwtService;
    import com.salon.auth_user_service.service.AuthService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.authentication.AuthenticationManager;
    import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
    import org.springframework.security.core.Authentication;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.stereotype.Service;
    import org.springframework.transaction.annotation.Transactional;

    import java.time.LocalDateTime;

    @Service
    @RequiredArgsConstructor
    public class AuthServiceImpl implements AuthService {

        private final UserRepository userRepository;
        private final UserMapper userMapper;
        private final PasswordEncoder passwordEncoder;
        private final AuthenticationManager authenticationManager;
        private final JwtService jwtService;
        private final RefreshTokenRepository refreshTokenRepository;
        private final CustomUserDetailsService customUserDetailsService;


        @Override
        public RegisterResponseDto register(RegisterRequestDto registerRequestDto) {

            if(userRepository.existsByEmail(registerRequestDto.getEmail())) {
                throw new UserAlreadyExistsException("Email already Registered");
            }

            if(userRepository.existsByContactNumber(registerRequestDto.getContactNumber())) {
                throw  new UserAlreadyExistsException("Contact Number already Registered");
            }

            User user = userMapper.toEntity(registerRequestDto);

            user.setPassword(passwordEncoder.encode(registerRequestDto.getPassword()));
            user.setRole(Role.Front_Desk);
            user.setActive(true);
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());

            User savedUser = userRepository.save(user);

            UserResponseDto userResponseDto = userMapper.toDto(savedUser);


            return RegisterResponseDto.builder()
                    .message("User registered successfully")
                    .user(userResponseDto)
                    .build();
        }

        @Override
        public LoginTokenResult login(LoginRequestDto loginRequestDto) {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                loginRequestDto.getEmail(),
                                    loginRequestDto.getPassword()
                    ));

            User user = userRepository.findByEmail(loginRequestDto.getEmail())
                    .orElseThrow(() -> new InvalidCredentialsException("Invalid Email or Password"));


            String accessToken = jwtService.generateAccessToken(
                    (UserDetails) authentication.getPrincipal());

            String refreshToken = jwtService.generateRefreshToken();

            String refreshTokenHash = jwtService.hashRefreshToken(refreshToken);

            LocalDateTime expiresAt = jwtService.getRefreshTokenExpiration();

            RefreshToken refreshTokenEntity =
                    RefreshToken.builder()
                            .user(user)
                            .tokenHash(refreshTokenHash)
                            .expiresAt(expiresAt)
                            .revoked(false)
                            .createdAt(LocalDateTime.now())
                            .build();

            refreshTokenRepository.save(refreshTokenEntity);

            UserResponseDto userResponseDto = userMapper.toDto(user);

            return LoginTokenResult.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .user(userResponseDto)
                    .build();
        }

        @Transactional
        @Override
        public LoginTokenResult refresh(String refreshToken) {
            if(refreshToken == null || refreshToken.isBlank()){
                throw new InvalidRefreshTokenException("Refresh token is missing");
            }

            String refreshTokenHash = jwtService.hashRefreshToken(refreshToken);

            RefreshToken existingToken = refreshTokenRepository
                    .findByTokenHash(refreshTokenHash)
                    .orElseThrow(() -> new InvalidRefreshTokenException("Invalid Refresh Token"));

            if(Boolean.TRUE.equals(existingToken.getRevoked())){
                throw new InvalidRefreshTokenException("Refresh token has been revoked");
            }

            if (!existingToken.getExpiresAt().isAfter(LocalDateTime.now())) {
                throw new InvalidRefreshTokenException("Refresh token has expired");
            }

            User user = existingToken.getUser();

            if (!Boolean.TRUE.equals(user.getActive())) {
                throw new InvalidRefreshTokenException(
                        "User account is inactive"
                );
            }

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());

            String newAccessToken = jwtService.generateAccessToken(userDetails);

            String newRefreshToken = jwtService.generateRefreshToken();

            String newRefreshTokenHash = jwtService.hashRefreshToken(newRefreshToken);

            LocalDateTime newExpiresAt = jwtService.getRefreshTokenExpiration();

            existingToken.setRevoked(true);
            refreshTokenRepository.save(existingToken);

            RefreshToken newRefreshTokenEntity =
                    RefreshToken.builder()
                            .user(user)
                            .tokenHash(newRefreshTokenHash)
                            .expiresAt(newExpiresAt)
                            .revoked(false)
                            .createdAt(LocalDateTime.now())
                            .build();
            refreshTokenRepository.save(newRefreshTokenEntity);

            return LoginTokenResult.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .user(userMapper.toDto(user))
                    .build();

        }

        @Override
        public void logout(String refreshToken) {
            if(refreshToken == null || refreshToken.isBlank()){
                return;
            }
            String refreshTokenHash = jwtService.hashRefreshToken(refreshToken);

            refreshTokenRepository.findByTokenHash(refreshTokenHash)
                    .ifPresent(refreshTokenEntity -> {
                        if(!Boolean.TRUE.equals(refreshTokenEntity.getRevoked())){
                            refreshTokenEntity.setRevoked(true);
                        }
                        refreshTokenRepository.save(refreshTokenEntity);
                    });
        }
    }
