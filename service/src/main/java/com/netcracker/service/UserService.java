package com.netcracker.service;

import com.netcracker.dto.UserDto;

import java.util.Optional;

public interface UserService {
    Optional<UserDto> getByLogin(String login);

    boolean loginAlreadyExist(String login);
}
