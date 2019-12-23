package com.netcracker.converter.impl;

import com.netcracker.converter.Converter;
import com.netcracker.dto.ClientDto;
import com.netcracker.dto.RoleDto;
import com.netcracker.entity.BaseEntity;
import com.netcracker.entity.Client;
import com.netcracker.entity.Role;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class ClientConverterImpl implements Converter<Client, ClientDto> {

    @Override
    public ClientDto toDto(Client entity) {
        List<Integer> eventList = entity.getEvents().stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
        List<Integer> commentList = entity.getComments().stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());
        List<Integer> requestList = entity.getRequests().stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());

        ClientDto clientDto = new ClientDto(entity.getId(), entity.getLogin(), entity.getPassword(),
                entity.getName(), entity.getSurname(), entity.getPhone(), entity.getEmail(),
                RoleDto.valueOf(entity.getRole().name()), entity.getDiscount(), entity.getTelegram(),
                eventList, commentList, requestList);
        return clientDto;
    }

    @Override
    public Client toEntity(ClientDto dto) {
        Client client = new Client(dto.getId(), dto.getLogin(), dto.getPassword(),
                dto.getName(), dto.getSurname(), Role.valueOf(dto.getRole().name()),
                dto.getPhone(), dto.getEmail(), dto.getDiscount(), dto.getTelegram());
        return client;
    }
}
