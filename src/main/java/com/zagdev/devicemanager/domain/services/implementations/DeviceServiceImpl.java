package com.zagdev.devicemanager.domain.services.implementations;

import com.zagdev.devicemanager.domain.dto.DeviceDto;
import com.zagdev.devicemanager.domain.entities.Device;
import com.zagdev.devicemanager.domain.repositories.DeviceRepository;
import com.zagdev.devicemanager.domain.services.DeviceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @Override
    public Page<DeviceDto> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Device> devicePage = deviceRepository.findAll(pageRequest);
        return devicePage.map(DeviceDto::fromEntity);
    }
}
