package com.salon.auth_user_service.service.impl;

import com.salon.auth_user_service.dto.inDto.ChangePasswordRequestDto;
import com.salon.auth_user_service.dto.inDto.UpdateUserRequestDto;
import com.salon.auth_user_service.dto.inDto.UpdateUserStatusRequestDto;
import com.salon.auth_user_service.dto.outDto.UserResponseDto;
import com.salon.auth_user_service.exception.InvalidCredentialsException;
import com.salon.auth_user_service.exception.UserAlreadyExistsException;
import com.salon.auth_user_service.mapper.UserMapper;
import com.salon.auth_user_service.model.User;
import com.salon.auth_user_service.repository.RefreshTokenRepository;
import com.salon.auth_user_service.repository.UserRepository;
import com.salon.auth_user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto getCurrentUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->
                        new UsernameNotFoundException("User not found with email: " + email)
                        );

        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->new UsernameNotFoundException("User not found with id: " + id)
                );
        return userMapper.toDto(user);
    }

    @Override
    public Page<UserResponseDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Override
    public UserResponseDto updateUser(Long id, UpdateUserRequestDto updateUserRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(()->
                        new UsernameNotFoundException("User not found with id: " + id)
                );

        if(updateUserRequestDto.getName() == null
                && updateUserRequestDto.getContactNumber() == null
                && updateUserRequestDto.getEmail() == null
        ){
            throw new IllegalArgumentException("At least one field is required for update");
        }

        if(updateUserRequestDto.getName() != null && !updateUserRequestDto.getName().isBlank()){
            user.setName(updateUserRequestDto.getName());
        }

        if(updateUserRequestDto.getContactNumber() != null && !updateUserRequestDto.getContactNumber().isBlank()){
            if (!java.util.Objects.equals(
                    user.getContactNumber(),
                    updateUserRequestDto.getContactNumber()
            )
                    && userRepository.existsByContactNumber(
                    updateUserRequestDto.getContactNumber()
            )) {

                throw new UserAlreadyExistsException(
                        "Contact Number already Registered"
                );
            }
            user.setContactNumber(updateUserRequestDto.getContactNumber());
        }

        if(updateUserRequestDto.getEmail() != null && !updateUserRequestDto.getEmail().isBlank()){
            if(!java.util.Objects.equals(
                    user.getEmail(),
                    updateUserRequestDto.getEmail())
                && userRepository.existsByEmail(updateUserRequestDto.getEmail())
            ){
                throw new UserAlreadyExistsException(
                        "Email already Registered"
                );
            }
            user.setEmail(updateUserRequestDto.getEmail());
        }

        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);

        return  userMapper.toDto(updatedUser);
    }

    @Override
    @Transactional
    public void updateUserStatus(Long id, UpdateUserStatusRequestDto request) {
        User user = userRepository.findById(id)
                .orElseThrow(()->
                        new UsernameNotFoundException("User not found with id: " + id)
                );
        Boolean newStatus = request.getActive();

        if(Objects.equals(user.getActive(), newStatus)){
            return;
        }

        user.setActive(newStatus);
        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        if(Boolean.FALSE.equals(newStatus)){
            refreshTokenRepository.deleteAllByUserId(id);
        }
    }

    @Override
    @Transactional
    public void changePassword(String email, ChangePasswordRequestDto request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->
                        new UsernameNotFoundException("User not found"));

        if(!passwordEncoder.matches(request.getCurrentPassword(),  user.getPassword())){
            throw new InvalidCredentialsException("Current password is incorrect");
        }

        if(!request.getNewPassword().equals(request.getConfirmPassword())){
            throw new IllegalArgumentException("New password and confirm password do not match");
        }

        if(passwordEncoder.matches(request.getNewPassword(),  user.getPassword())){
            throw new IllegalArgumentException("New password must be different from current password");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        user.setUpdatedAt(LocalDateTime.now());

        userRepository.save(user);

        refreshTokenRepository.deleteAllByUserId(user.getId());
    }
}
