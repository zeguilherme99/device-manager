package com.zagdev.devicemanager.domain.usecases.implementations;

import com.zagdev.devicemanager.domain.dto.DeviceDto;
import com.zagdev.devicemanager.domain.exceptions.DataNotFoundException;
import com.zagdev.devicemanager.domain.services.DeviceService;
import com.zagdev.devicemanager.domain.usecases.DeviceUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeviceUseCaseImpl implements DeviceUseCase {

    private final Logger logger = LoggerFactory.getLogger(DeviceUseCaseImpl.class);

    private final DeviceService deviceService;

    public DeviceUseCaseImpl(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public DeviceDto create(DeviceDto request) {
        logger.info("Controller: Responding with device id [{}]", request.id());
        DeviceDto dto = deviceService.create(request);
        logger.info("UseCase: Created device with id [{}]", dto.id());
        return dto;
    }

    @Override
    public Page<DeviceDto> findAll(int page, int size) {
        logger.info("UseCase: Fetching devices for page [{}], size [{}]", page, size);
        Page<DeviceDto> devices = deviceService.findAll(page, size);
        logger.info("UseCase: Returning [{}] devices for page [{}]", devices.getContent().size(), page);
        return devices;
    }

    @Override
    public DeviceDto findById(UUID id) throws DataNotFoundException {
        logger.info("UseCase: Fetching device by id [{}]", id);
        DeviceDto dto = deviceService.findById(id);
        logger.info("UseCase: Found device with id [{}]", dto.id());
        return dto;
    }
}
