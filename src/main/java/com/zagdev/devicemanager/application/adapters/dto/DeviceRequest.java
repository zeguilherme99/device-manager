package com.zagdev.devicemanager.application.adapters.dto;

import jakarta.validation.constraints.NotBlank;

public record DeviceRequest(
        @NotBlank String name,
        @NotBlank String type
) {}
