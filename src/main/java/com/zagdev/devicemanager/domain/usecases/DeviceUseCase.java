package com.zagdev.devicemanager.domain.usecases;

import com.zagdev.devicemanager.domain.dto.DeviceDto;
import org.springframework.data.domain.Page;

public interface DeviceUseCase {
    DeviceDto create(DeviceDto request);
    Page<DeviceDto> findAll(int page, int size);
}
