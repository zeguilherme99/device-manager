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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
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

    @Test
    void shouldListDevices() {
        int page = 0;
        int size = 2;
        Instant now = Instant.now();

        Device device1 = new Device(UUID.randomUUID(), "Device1", DeviceType.IOT, now.minusSeconds(60));
        Device device2 = new Device(UUID.randomUUID(), "Device2", DeviceType.MODEM, now);

        Page<Device> devicePage = new PageImpl<>(List.of(device2, device1));
        when(deviceRepository.findAll(any(Pageable.class))).thenReturn(devicePage);

        Page<DeviceDto> result = deviceService.findAll(page, size);

        assertEquals(2, result.getTotalElements());
        assertEquals("Device2", result.getContent().get(0).name());
        assertEquals("Device1", result.getContent().get(1).name());
        verify(deviceRepository, times(1)).findAll(any(Pageable.class));
    }
}