package com.zagdev.devicemanager.domain.dto;

import com.zagdev.devicemanager.infrastructure.dto.UserReqresDto;

public record UserDto(
        int id,
        String email,
        String firstName,
        String lastName,
        String avatar
) {

    public static UserDto from(UserReqresDto userReqresDto) {
        return new UserDto(
                userReqresDto.id(),
                userReqresDto.email(),
                userReqresDto.firstName(),
                userReqresDto.lastName(),
                userReqresDto.avatar()
        );
    }
}
