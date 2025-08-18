package com.zagdev.devicemanager.domain.usecases.implementations;

import com.zagdev.devicemanager.domain.dto.UserDto;
import com.zagdev.devicemanager.domain.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserUseCaseImplTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserUseCaseImpl userUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetUsers() {
        List<UserDto> userDtos = List.of(new UserDto(1, "email", "firstName", "lastName", "avatar"));
        when(userService.getUsers(1)).thenReturn(userDtos);

        List<UserDto> result = userUseCase.getUsers(1);

        assertEquals(1, result.size());
        assertEquals("firstName", result.get(0).firstName());
        assertEquals("email", result.get(0).email());
        assertEquals("avatar", result.get(0).avatar());
        assertEquals("lastName", result.get(0).lastName());
        assertEquals(1, result.get(0).id());

        verify(userService).getUsers(1);
    }

}