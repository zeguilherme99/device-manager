package com.zagdev.devicemanager.domain.services;

import com.zagdev.devicemanager.domain.dto.DeviceDto;

public interface DeviceService {

    DeviceDto create(DeviceDto request);
}
