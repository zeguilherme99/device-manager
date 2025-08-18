package com.zagdev.devicemanager.application.adapters.controllers;

import com.zagdev.devicemanager.domain.dto.UserDto;
import com.zagdev.devicemanager.domain.usecases.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserUseCase userUseCase;

    @InjectMocks
    private UserController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldListUsers() throws Exception {
        UserDto user1 = new UserDto(1, "email1","firstName1", "lastName1",  "avatar1");
        UserDto user2 = new UserDto(2, "email2","firstName2", "lastName2",  "avatar2");
        when(userUseCase.getUsers(2)).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/users")
                        .param("page", "2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].firstName").value("firstName1"))
                .andExpect(jsonPath("$[0].lastName").value("lastName1"))
                .andExpect(jsonPath("$[0].email").value("email1"))
                .andExpect(jsonPath("$[0].avatar").value("avatar1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].firstName").value("firstName2"))
                .andExpect(jsonPath("$[1].lastName").value("lastName2"))
                .andExpect(jsonPath("$[1].email").value("email2"))
                .andExpect(jsonPath("$[1].avatar").value("avatar2"));
    }
}