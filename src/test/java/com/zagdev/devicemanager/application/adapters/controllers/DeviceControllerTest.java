package com.zagdev.devicemanager.application.adapters.controllers;

import com.zagdev.devicemanager.domain.dto.DeviceDto;
import com.zagdev.devicemanager.domain.usecases.DeviceUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.Mockito.when;
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
}