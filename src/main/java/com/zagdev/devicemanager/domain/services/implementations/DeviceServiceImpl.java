package com.zagdev.devicemanager.domain.services.implementations;

import com.zagdev.devicemanager.domain.dto.DeviceDto;
import com.zagdev.devicemanager.domain.entities.Device;
import com.zagdev.devicemanager.domain.exceptions.DataNotFoundException;
import com.zagdev.devicemanager.domain.exceptions.ErrorCode;
import com.zagdev.devicemanager.domain.repositories.DeviceRepository;
import com.zagdev.devicemanager.domain.services.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public DeviceDto create(DeviceDto request) {
        logger.info("Service: Saving device with name [{}]", request.name());
        Device device = Device.fromDto(request);
        Device saved = deviceRepository.save(device);
        logger.info("Service: Saved device with id [{}] and name [{}]", saved.getId(), saved.getName());
        return DeviceDto.fromEntity(saved);
    }

    @Override
    public Page<DeviceDto> findAll(int page, int size) {
        logger.info("Service: Fetching all devices, page [{}], size [{}]", page, size);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Device> devicePage = deviceRepository.findAll(pageRequest);
        logger.info("Service: Found [{}] devices for page [{}]", devicePage.getContent().size(), page);
        return devicePage.map(DeviceDto::fromEntity);
    }

    @Override
    public DeviceDto findById(UUID id) throws DataNotFoundException {
        logger.info("Service: Fetching device by id [{}]", id);
        Optional<Device> deviceOptional = deviceRepository.findById(id);

        if (deviceOptional.isEmpty()) {
            logger.warn("Service: Device not found for id [{}]", id);
            throw new DataNotFoundException(ErrorCode.DEVICE_NOT_FOUND);
        }

        Device device = deviceOptional.get();
        logger.info("Service: Found device with id [{}]", device.getId());
        return DeviceDto.fromEntity(device);
    }
}
