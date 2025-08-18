package com.zagdev.devicemanager.domain.usecases.implementations;

import com.zagdev.devicemanager.domain.dto.DeviceDto;
import com.zagdev.devicemanager.domain.services.DeviceService;
import com.zagdev.devicemanager.domain.usecases.DeviceUseCase;
import org.springframework.stereotype.Service;

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
}
