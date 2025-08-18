package com.zagdev.devicemanager.infrastructure.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record UserReqresDto(
        int id,
        String email,
        String firstName,
        String lastName,
        String avatar
) {
}
