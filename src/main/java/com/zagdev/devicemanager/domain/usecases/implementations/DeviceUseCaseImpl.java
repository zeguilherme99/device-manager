package com.zagdev.devicemanager.domain.usecases.implementations;

import com.zagdev.devicemanager.domain.dto.DeviceDto;
import com.zagdev.devicemanager.domain.exceptions.DataNotFoundException;
import com.zagdev.devicemanager.domain.services.DeviceService;
import com.zagdev.devicemanager.domain.usecases.DeviceUseCase;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeviceUseCaseImpl implements DeviceUseCase {

    private final DeviceService deviceService;

    public DeviceUseCaseImpl(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public DeviceDto create(DeviceDto request) {
        DeviceDto dto = deviceService.create(request);
        return dto;
    }

    @Override
    public Page<DeviceDto> findAll(int page, int size) {
        return deviceService.findAll(page, size);
    }

    @Override
    public DeviceDto findById(UUID id) throws DataNotFoundException {
        return deviceService.findById(id);
    }
}
