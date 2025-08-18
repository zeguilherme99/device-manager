package com.zagdev.devicemanager.application.adapters.controllers;


import com.zagdev.devicemanager.application.adapters.dto.DeviceRequest;
import com.zagdev.devicemanager.application.adapters.dto.DeviceResponse;
import com.zagdev.devicemanager.domain.dto.DeviceDto;
import com.zagdev.devicemanager.domain.exceptions.DataNotFoundException;
import com.zagdev.devicemanager.domain.usecases.DeviceUseCase;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/devices")
public class DeviceController {

    private final DeviceUseCase deviceUseCase;

    public DeviceController(DeviceUseCase deviceUseCase) {
        this.deviceUseCase = deviceUseCase;
    }

    @PostMapping
    public ResponseEntity<DeviceResponse> createDevice(@Valid @RequestBody DeviceRequest request) {
        DeviceDto deviceDto = DeviceDto.fromRequest(request);
        DeviceDto result = deviceUseCase.create(deviceDto);
        DeviceResponse resp = DeviceResponse.from(result);
        return ResponseEntity
                .created(URI.create("/devices/" + result.id()))
                .body(resp);
    }

    @GetMapping
    public ResponseEntity<List<DeviceResponse>> listDevices(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        Page<DeviceDto> devicePage = deviceUseCase.findAll(page, size);
        Page<DeviceResponse> responsePage = devicePage.map(DeviceResponse::from);
        return ResponseEntity.ok(responsePage.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> getDeviceById(@PathVariable UUID id) throws DataNotFoundException {
        DeviceDto deviceDto = deviceUseCase.findById(id);
        DeviceResponse response = DeviceResponse.from(deviceDto);
        return ResponseEntity.ok(response);
    }
}
