package com.zagdev.devicemanager.domain.dto;

import com.zagdev.devicemanager.application.adapters.dto.DeviceRequest;
import com.zagdev.devicemanager.domain.entities.Device;

import java.time.Instant;
import java.util.UUID;

public record DeviceDto(UUID id, String name, String type, Instant createdAt) {

    public static DeviceDto fromEntity(Device deviceEntity) {
        return new DeviceDto(
                deviceEntity.getId(),
                deviceEntity.getName(),
                deviceEntity.getType().name(),
                deviceEntity.getCreatedAt()
        );
    }

    public static DeviceDto fromRequest(DeviceRequest request) {
        return new DeviceDto(
                null,
                request.name(),
                request.type(),
                Instant.now()
        );
    }
}
