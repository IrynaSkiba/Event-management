package com.netcracker.service;

import com.netcracker.dto.ClientDto;

import java.util.Optional;

public interface ClientService {
    Optional<ClientDto> getByLogin(String login);

    boolean loginAlreadyExist(String login);
}
