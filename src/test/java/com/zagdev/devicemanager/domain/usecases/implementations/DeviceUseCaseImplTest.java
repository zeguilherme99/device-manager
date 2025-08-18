package com.zagdev.devicemanager.domain.usecases.implementations;

import com.zagdev.devicemanager.domain.dto.DeviceDto;
import com.zagdev.devicemanager.domain.services.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeviceUseCaseImplTest {

    @Mock
    private DeviceService deviceService;

    @InjectMocks
    private DeviceUseCaseImpl deviceUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateDevice() {
        UUID id = UUID.randomUUID();
        String name = "Device Test";
        String type = "sensor";
        Instant createdAt = Instant.now();

        DeviceDto request = new DeviceDto(null, name, type, createdAt);
        DeviceDto response = new DeviceDto(id, name, type, createdAt);

        when(deviceService.create(any(DeviceDto.class))).thenReturn(response);

        DeviceDto result = deviceUseCase.create(request);

        assertEquals(response, result);
        verify(deviceService).create(request);
    }

}