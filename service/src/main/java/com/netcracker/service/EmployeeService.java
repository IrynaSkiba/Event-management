package com.netcracker.service;

import com.netcracker.dto.EmployeeDto;
import com.netcracker.dto.EventDto;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Optional<EmployeeDto> getByLogin(String login);

    boolean loginAlreadyExist(String login);

    List<EventDto> getEvents(int id);
}
