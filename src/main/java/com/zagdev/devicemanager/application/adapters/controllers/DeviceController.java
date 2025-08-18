package com.zagdev.devicemanager.application.adapters.controllers;


import com.zagdev.devicemanager.application.adapters.dto.DeviceRequest;
import com.zagdev.devicemanager.application.adapters.dto.DeviceResponse;
import com.zagdev.devicemanager.domain.dto.DeviceDto;
import com.zagdev.devicemanager.domain.exceptions.DataNotFoundException;
import com.zagdev.devicemanager.domain.usecases.DeviceUseCase;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    private final DeviceUseCase deviceUseCase;

    public DeviceController(DeviceUseCase deviceUseCase) {
        this.deviceUseCase = deviceUseCase;
    }

    @PostMapping
    public ResponseEntity<DeviceResponse> createDevice(@Valid @RequestBody DeviceRequest request) {
        logger.info("Controller: Received request to create device for name [{}]", request.name());
        DeviceDto deviceDto = DeviceDto.fromRequest(request);
        DeviceDto result = deviceUseCase.create(deviceDto);
        DeviceResponse resp = DeviceResponse.from(result);
        logger.info("Controller: Created device with id [{}]", resp.id());
        return ResponseEntity
                .created(URI.create("/devices/" + result.id()))
                .body(resp);
    }

    @GetMapping
    public ResponseEntity<List<DeviceResponse>> listDevices(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        logger.info("Controller: Received request to list devices, page [{}]", page);
        Page<DeviceDto> devicePage = deviceUseCase.findAll(page, size);
        Page<DeviceResponse> responsePage = devicePage.map(DeviceResponse::from);
        List<DeviceResponse> deviceResponses = responsePage.getContent();
        logger.info("Controller: Responding with [{}] devices for page [{}]", deviceResponses.size(), page);
        return ResponseEntity.ok(deviceResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> getDeviceById(@PathVariable UUID id) throws DataNotFoundException {
        logger.info("Controller: Received request to get device by id [{}]", id);
        DeviceDto deviceDto = deviceUseCase.findById(id);
        DeviceResponse response = DeviceResponse.from(deviceDto);
        logger.info("Controller: Responding with device id [{}]", response.id());
        return ResponseEntity.ok(response);
    }
}
