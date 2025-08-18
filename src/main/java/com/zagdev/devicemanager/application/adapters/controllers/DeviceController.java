package com.zagdev.devicemanager.application.adapters.controllers;


import com.zagdev.devicemanager.application.adapters.config.ResponseError;
import com.zagdev.devicemanager.application.adapters.dto.DeviceRequest;
import com.zagdev.devicemanager.application.adapters.dto.DeviceResponse;
import com.zagdev.devicemanager.domain.dto.DeviceDto;
import com.zagdev.devicemanager.domain.exceptions.DataNotFoundException;
import com.zagdev.devicemanager.domain.usecases.DeviceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/devices")
@Tag(name = "Device", description = "Device Manager API")
public class DeviceController {

    private final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    private final DeviceUseCase deviceUseCase;

    public DeviceController(DeviceUseCase deviceUseCase) {
        this.deviceUseCase = deviceUseCase;
    }

    @PostMapping
    @Operation(summary = "Create new device", description = "Creates a new device with the data provided in the request body.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Device successfully created",
                    content = @Content(schema = @Schema(implementation = DeviceResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content(schema = @Schema(implementation = ResponseError.ResponseErrorMessage.class)))
    })
    public ResponseEntity<DeviceResponse> createDevice(@Valid @RequestBody DeviceRequest request) {
        logger.info("Controller: Received request to create device for name [{}]", request.name());
        DeviceDto deviceDto = DeviceDto.fromRequest(request);
        DeviceDto result = deviceUseCase.create(deviceDto);
        DeviceResponse resp = DeviceResponse.from(result);
        logger.info("Controller: Created device with id [{}]", resp.id());
        return ResponseEntity
                .created(URI.create("/devices/" + result.id()))
                .body(resp);
    }

    @GetMapping
    @Operation(summary = "List devices", description = "Returns a paginated list of devices.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of devices returned successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = DeviceResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = ResponseError.ResponseErrorMessage.class)))
    })
    public ResponseEntity<List<DeviceResponse>> listDevices(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        logger.info("Controller: Received request to list devices, page [{}]", page);
        Page<DeviceDto> devicePage = deviceUseCase.findAll(page, size);
        Page<DeviceResponse> responsePage = devicePage.map(DeviceResponse::from);
        List<DeviceResponse> deviceResponses = responsePage.getContent();
        logger.info("Controller: Responding with [{}] devices for page [{}]", deviceResponses.size(), page);
        return ResponseEntity.ok(deviceResponses);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get device by ID", description = "Returns the data of a device by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device found",
                    content = @Content(schema = @Schema(implementation = DeviceResponse.class))),
            @ApiResponse(responseCode = "404", description = "Device not found",
                    content = @Content(schema = @Schema(implementation = ResponseError.ResponseErrorMessage.class)))
    })
    public ResponseEntity<DeviceResponse> getDeviceById(@PathVariable UUID id) throws DataNotFoundException {
        logger.info("Controller: Received request to get device by id [{}]", id);
        DeviceDto deviceDto = deviceUseCase.findById(id);
        DeviceResponse response = DeviceResponse.from(deviceDto);
        logger.info("Controller: Responding with device id [{}]", response.id());
        return ResponseEntity.ok(response);
    }
}
