package com.zagdev.devicemanager.domain.services.implementations;

import com.zagdev.devicemanager.domain.dto.UserDto;
import com.zagdev.devicemanager.domain.services.UserService;
import com.zagdev.devicemanager.infrastructure.ReqresClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final ReqresClient reqresClient;

    public UserServiceImpl(ReqresClient reqresClient) {
        this.reqresClient = reqresClient;
    }

    @Override
    public List<UserDto> getUsers(int page) {
        return reqresClient.getUsers(page).data().stream().map(UserDto::from).toList();
    }
}
