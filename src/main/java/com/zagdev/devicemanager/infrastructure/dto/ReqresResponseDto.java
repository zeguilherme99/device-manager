package com.zagdev.devicemanager.infrastructure.dto;

import java.util.List;

public record ReqresResponseDto(
        int page,
        int perPage,
        int total,
        int totalPages,
        List<UserReqresDto> data
) {
}
