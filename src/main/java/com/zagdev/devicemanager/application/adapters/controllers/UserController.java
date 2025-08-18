package com.zagdev.devicemanager.application.adapters.controllers;

import com.zagdev.devicemanager.application.adapters.dto.UserResponse;
import com.zagdev.devicemanager.domain.dto.UserDto;
import com.zagdev.devicemanager.domain.usecases.UserUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers(@RequestParam(required = false, defaultValue = "2") int page) {
        List<UserDto> users = userUseCase.getUsers(page);
        List<UserResponse> userResponses = users.stream().map(UserResponse::from).toList();
        return ResponseEntity.ok(userResponses);
    }
}
