package com.zagdev.devicemanager.application.adapters.dto;

import com.zagdev.devicemanager.domain.dto.DeviceDto;

import java.time.Instant;
import java.util.UUID;

public record DeviceResponse(
        UUID id,
        String name,
        String type,
        Instant createdAt
) {
    public static DeviceResponse from(DeviceDto dto) {
        return new DeviceResponse(dto.id(), dto.name(), dto.type(), dto.createdAt());
    }
}
