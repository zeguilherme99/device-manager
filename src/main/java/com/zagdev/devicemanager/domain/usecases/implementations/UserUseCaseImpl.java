package com.zagdev.devicemanager.domain.usecases.implementations;

import com.zagdev.devicemanager.domain.dto.UserDto;
import com.zagdev.devicemanager.domain.services.UserService;
import com.zagdev.devicemanager.domain.usecases.UserUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserUseCaseImpl implements UserUseCase {

    private final Logger logger = LoggerFactory.getLogger(UserUseCaseImpl.class);

    private final UserService userService;

    public UserUseCaseImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<UserDto> getUsers(int page) {
        logger.info("UseCase: Fetching users for page [{}]", page);
        List<UserDto> users =  userService.getUsers(page);
        logger.info("UseCase: Returning [{}] users for page [{}]", users.size(), page);
        return users;
    }
}
