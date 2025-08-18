package com.zagdev.devicemanager.domain.services.implementations;

import com.zagdev.devicemanager.domain.dto.DeviceDto;
import com.zagdev.devicemanager.domain.entities.Device;
import com.zagdev.devicemanager.domain.repositories.DeviceRepository;
import com.zagdev.devicemanager.domain.services.DeviceService;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public DeviceDto create(DeviceDto request) {
        Device device = Device.fromDto(request);
        Device saved = deviceRepository.save(device);
        return DeviceDto.fromEntity(saved);
    }
}
