package com.zagdev.devicemanager.application.adapters.controllers;

import com.zagdev.devicemanager.domain.dto.DeviceDto;
import com.zagdev.devicemanager.domain.usecases.DeviceUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class DeviceControllerTest {
    private MockMvc mockMvc;

    @Mock
    private DeviceUseCase deviceUseCase;

    @InjectMocks
    private DeviceController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldCreateDevice() throws Exception {
        UUID id = UUID.randomUUID();
        String name = "New Device";
        String type = "SMARTPHONE";
        Instant createdAt = Instant.now();

        DeviceDto dtoResponse = new DeviceDto(id, name, type, createdAt);

        when(deviceUseCase.create(ArgumentMatchers.any(DeviceDto.class))).thenReturn(dtoResponse);

        String jsonRequest = String.format("{\"name\":\"%s\",\"type\":\"%s\"}", name, type);

        mockMvc.perform(post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/devices/" + id))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.type").value(type));
    }

    @Test
    void shouldListDevices() throws Exception {
        DeviceDto dto1 = new DeviceDto(UUID.randomUUID(), "Device1", "SENSOR", Instant.now());
        DeviceDto dto2 = new DeviceDto(UUID.randomUUID(), "Device2", "MODEM", Instant.now());
        Page<DeviceDto> page = new PageImpl<>(List.of(dto1, dto2));

        when(deviceUseCase.findAll(0, 10)).thenReturn(page);

        mockMvc.perform(get("/devices")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Device1"))
                .andExpect(jsonPath("$[1].name").value("Device2"));
    }

    @Test
    void shouldGetDeviceById() throws Exception {
        UUID id = UUID.randomUUID();
        String name = "Device Test";
        String type = "SENSOR";
        Instant createdAt = Instant.now();
        DeviceDto dto = new DeviceDto(id, name, type, createdAt);

        when(deviceUseCase.findById(id)).thenReturn(dto);

        mockMvc.perform(get("/devices/{id}", id.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.type").value(type));
    }
}