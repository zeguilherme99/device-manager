package com.zagdev.devicemanager.domain.usecases.implementations;

import com.zagdev.devicemanager.domain.dto.DeviceDto;
import com.zagdev.devicemanager.domain.exceptions.DataNotFoundException;
import com.zagdev.devicemanager.domain.services.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void shouldListDevices() {
        DeviceDto dto1 = new DeviceDto(UUID.randomUUID(), "Device1", "SENSOR", Instant.now());
        DeviceDto dto2 = new DeviceDto(UUID.randomUUID(), "Device2", "MODEM", Instant.now());
        Page<DeviceDto> page = new PageImpl<>(List.of(dto1, dto2));

        when(deviceService.findAll(0, 10)).thenReturn(page);

        Page<DeviceDto> result = deviceUseCase.findAll(0, 10);

        assertEquals(2, result.getTotalElements());
        assertEquals("Device1", result.getContent().get(0).name());
        assertEquals("Device2", result.getContent().get(1).name());
    }

    @Test
    void getDeviceById() throws DataNotFoundException {
        UUID id = UUID.randomUUID();
        DeviceDto dto = new DeviceDto(id, "Device Test", "SENSOR", Instant.now());

        when(deviceService.findById(id)).thenReturn(dto);

        DeviceDto result = deviceUseCase.findById(id);

        assertEquals(dto, result);
        verify(deviceService).findById(id);
    }
}