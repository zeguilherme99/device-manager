package com.zagdev.devicemanager.application.adapters.dto;

import com.zagdev.devicemanager.domain.dto.UserDto;

public record UserResponse(
        int id,
        String email,
        String firstName,
        String lastName,
        String avatar
) {

    public static UserResponse from(UserDto dto) {
        return new UserResponse(dto.id(), dto.email(), dto.firstName(), dto.lastName(), dto.avatar());
    }
}
