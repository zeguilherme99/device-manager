package com.zagdev.devicemanager.domain.usecases;

import com.zagdev.devicemanager.domain.dto.UserDto;

import java.util.List;

public interface UserUseCase {

    List<UserDto> getUsers(int page);
}
