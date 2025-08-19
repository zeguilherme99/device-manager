package com.zagdev.devicemanager.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zagdev.devicemanager.application.adapters.dto.DeviceRequest;
import com.zagdev.devicemanager.domain.entities.Device;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DeviceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateDeviceSuccessfully() throws Exception {
        DeviceRequest request = new DeviceRequest("Device 1", "Type A");
        mockMvc.perform(post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Device 1"));
    }

    @Test
    void shouldReturnBadRequestWhenCreateDeviceWithInvalidData() throws Exception {
        DeviceRequest request = new DeviceRequest("", "");
        mockMvc.perform(post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldListDevices() throws Exception {
        mockMvc.perform(get("/devices")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldGetDeviceByIdSuccessfully() throws Exception {
        DeviceRequest request = new DeviceRequest("Device 2", "Type B");
        String response = mockMvc.perform(post("/devices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();
        Device device = objectMapper.readValue(response, Device.class);

        mockMvc.perform(get("/devices/" + device.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Device 2"));
    }

    @Test
    void shouldReturnNotFoundWhenDeviceDoesNotExist() throws Exception {
        mockMvc.perform(get("/devices/" + UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}
