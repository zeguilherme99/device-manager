package com.zagdev.devicemanager.domain.services.implementations;

import com.zagdev.devicemanager.domain.dto.UserDto;
import com.zagdev.devicemanager.infrastructure.ReqresClient;
import com.zagdev.devicemanager.infrastructure.dto.ReqresResponseDto;
import com.zagdev.devicemanager.infrastructure.dto.UserReqresDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private ReqresClient reqresClient;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetUsers() {
        int page = 1;
        UserReqresDto userData = new UserReqresDto(1, "email", "firstName", "lastName", "avatar");
        ReqresResponseDto responseDto = new ReqresResponseDto(page, 6, 12, 2, List.of(userData));
        when(reqresClient.getUsers(page)).thenReturn(responseDto);

        List<UserDto> users = userService.getUsers(page);

        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("firstName", users.get(0).firstName());
        assertEquals("lastName", users.get(0).lastName());
        assertEquals("email", users.get(0).email());
        assertEquals("avatar", users.get(0).avatar());
        assertEquals(1, users.get(0).id());

        verify(reqresClient).getUsers(page);
    }
}