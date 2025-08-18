package com.zagdev.devicemanager.domain.usecases.implementations;

import com.zagdev.devicemanager.domain.dto.UserDto;
import com.zagdev.devicemanager.domain.services.UserService;
import com.zagdev.devicemanager.domain.usecases.UserUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserUseCaseImpl implements UserUseCase {

    private final UserService userService;

    public UserUseCaseImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<UserDto> getUsers(int page) {
        return userService.getUsers(page);
    }
}
