package com.salon.auth_user_service.dto.outDto;

import com.salon.auth_user_service.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
    private String contactNumber;
    private Role role;
    private Boolean active;
}
