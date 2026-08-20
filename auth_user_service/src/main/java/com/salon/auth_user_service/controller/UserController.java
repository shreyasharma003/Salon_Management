package com.salon.auth_user_service.controller;

import com.salon.auth_user_service.dto.inDto.ChangePasswordRequestDto;
import com.salon.auth_user_service.dto.inDto.UpdateUserRequestDto;
import com.salon.auth_user_service.dto.inDto.UpdateUserStatusRequestDto;
import com.salon.auth_user_service.dto.outDto.UserResponseDto;
import com.salon.auth_user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser(Authentication authentication){
        String email = authentication.getName();
        UserResponseDto userResponseDto = userService.getCurrentUser(email);

        return ResponseEntity.ok(userResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id){
        UserResponseDto userResponseDto = userService.getUserById(id);

        return  ResponseEntity.ok(userResponseDto);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(Pageable pageable){
        Page<UserResponseDto> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequestDto updateUserRequestDto
            ){
        UserResponseDto userResponseDto = userService.updateUser(id, updateUserRequestDto);

        return  ResponseEntity.ok(userResponseDto);
    }

    @PatchMapping("/{id}/status")
    public  ResponseEntity<Void> updateUserStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserStatusRequestDto updateUserStatusRequestDto
    ){
        userService.updateUserStatus(id, updateUserStatusRequestDto);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/me/password")
    public ResponseEntity<Void> changePassword(
            Authentication authentication,
            @Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto
    ){
        String email = authentication.getName();
        userService.changePassword(email, changePasswordRequestDto);

        return ResponseEntity.noContent().build();
    }
}
