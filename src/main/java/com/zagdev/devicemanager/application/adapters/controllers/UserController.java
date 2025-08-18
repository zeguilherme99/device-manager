package com.zagdev.devicemanager.application.adapters.controllers;

import com.zagdev.devicemanager.application.adapters.dto.UserResponse;
import com.zagdev.devicemanager.domain.dto.UserDto;
import com.zagdev.devicemanager.domain.usecases.UserUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers(@RequestParam(required = false, defaultValue = "2") int page) {
        logger.info("Controller: Received request to list users, page [{}]", page);
        List<UserDto> users = userUseCase.getUsers(page);
        List<UserResponse> userResponses = users.stream().map(UserResponse::from).toList();
        logger.info("Controller: Responding with [{}] users for page [{}]", userResponses.size(), page);
        return ResponseEntity.ok(userResponses);
    }
}
