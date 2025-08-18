package com.zagdev.devicemanager.domain.services.implementations;

import com.zagdev.devicemanager.domain.dto.DeviceDto;
import com.zagdev.devicemanager.domain.entities.Device;
import com.zagdev.devicemanager.domain.enums.DeviceType;
import com.zagdev.devicemanager.domain.repositories.DeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DeviceServiceImplTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeviceServiceImpl deviceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        UUID deviceId = UUID.randomUUID();
        Instant createdAt = Instant.now();

        DeviceDto request = new DeviceDto(deviceId, "DeviceName", "DeviceType", createdAt);
        Device savedDevice = new Device(deviceId, "DeviceName", DeviceType.MODEM, createdAt);
        DeviceDto expectedResponse = new DeviceDto(deviceId, "DeviceName", "modem", createdAt);

        when(deviceRepository.save(any(Device.class))).thenReturn(savedDevice);

        DeviceDto response = deviceService.create(request);

        assertEquals(expectedResponse, response);
        verify(deviceRepository, times(1)).save(any(Device.class));
    }
}