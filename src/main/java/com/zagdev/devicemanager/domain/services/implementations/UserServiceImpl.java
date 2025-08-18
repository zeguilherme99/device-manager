package com.zagdev.devicemanager.domain.services.implementations;

import com.zagdev.devicemanager.domain.dto.UserDto;
import com.zagdev.devicemanager.domain.services.UserService;
import com.zagdev.devicemanager.infrastructure.ReqresClient;
import com.zagdev.devicemanager.infrastructure.dto.ReqresResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final ReqresClient reqresClient;

    public UserServiceImpl(ReqresClient reqresClient) {
        this.reqresClient = reqresClient;
    }

    @Override
    public List<UserDto> getUsers(int page) {
        logger.info("Service: Calling ReqresClient to fetch users for page [{}]", page);
        ReqresResponseDto responseDto =  reqresClient.getUsers(page);
        logger.info("Service: ReqresClient returned [{}] users for page [{}]", responseDto.data().size(), page);
        return responseDto.data().stream().map(UserDto::from).toList();
    }
}
