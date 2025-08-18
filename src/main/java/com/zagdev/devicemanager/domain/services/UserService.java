package com.zagdev.devicemanager.domain.services;

import com.zagdev.devicemanager.domain.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getUsers(int page);
}
