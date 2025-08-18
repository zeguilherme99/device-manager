package com.zagdev.devicemanager.application.adapters.controllers;

import com.zagdev.devicemanager.application.adapters.config.ResponseError;
import com.zagdev.devicemanager.application.adapters.dto.UserResponse;
import com.zagdev.devicemanager.domain.dto.UserDto;
import com.zagdev.devicemanager.domain.usecases.UserUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Users", description = "User Management API")
@RequestMapping("/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @GetMapping
    @Operation(summary = "List users", description = "Returns a paginated list of users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users returned successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ResponseError.ResponseErrorMessage.class)))
    })
    public ResponseEntity<List<UserResponse>> getUsers(@RequestParam(required = false, defaultValue = "2") int page) {
        logger.info("Controller: Received request to list users, page [{}]", page);
        List<UserDto> users = userUseCase.getUsers(page);
        List<UserResponse> userResponses = users.stream().map(UserResponse::from).toList();
        logger.info("Controller: Responding with [{}] users for page [{}]", userResponses.size(), page);
        return ResponseEntity.ok(userResponses);
    }
}
