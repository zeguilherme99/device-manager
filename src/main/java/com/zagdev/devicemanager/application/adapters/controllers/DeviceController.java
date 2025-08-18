package com.zagdev.devicemanager.application.adapters.controllers;


import com.zagdev.devicemanager.application.adapters.dto.DeviceRequest;
import com.zagdev.devicemanager.application.adapters.dto.DeviceResponse;
import com.zagdev.devicemanager.domain.dto.DeviceDto;
import com.zagdev.devicemanager.domain.usecases.DeviceUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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
}
