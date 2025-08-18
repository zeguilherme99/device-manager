package com.zagdev.devicemanager.domain.services;

import com.zagdev.devicemanager.domain.dto.DeviceDto;
import com.zagdev.devicemanager.domain.exceptions.DataNotFoundException;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface DeviceService {

    DeviceDto create(DeviceDto request);
    Page<DeviceDto> findAll(int page, int size);
    DeviceDto findById(UUID id) throws DataNotFoundException;
}
