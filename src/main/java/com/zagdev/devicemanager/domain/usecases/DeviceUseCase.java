package com.zagdev.devicemanager.domain.usecases;

import com.zagdev.devicemanager.domain.dto.DeviceDto;

public interface DeviceUseCase {
    DeviceDto create(DeviceDto request);
}
