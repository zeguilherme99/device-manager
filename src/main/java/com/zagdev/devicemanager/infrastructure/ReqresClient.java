package com.zagdev.devicemanager.infrastructure;

import com.zagdev.devicemanager.infrastructure.dto.ReqresResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "reqresClient", url = "https://reqres.in")
public interface ReqresClient {

    @GetMapping("/api/users")
    ReqresResponseDto getUsers(@RequestParam("page") int page);
}