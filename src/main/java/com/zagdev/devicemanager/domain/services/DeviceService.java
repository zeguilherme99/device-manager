package com.zagdev.devicemanager.domain.services;

import com.zagdev.devicemanager.domain.dto.DeviceDto;
import org.springframework.data.domain.Page;

public interface DeviceService {

    DeviceDto create(DeviceDto request);
    Page<DeviceDto> findAll(int page, int size);
}
