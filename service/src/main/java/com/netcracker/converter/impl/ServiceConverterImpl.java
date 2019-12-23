package com.netcracker.converter.impl;

import com.netcracker.converter.Converter;
import com.netcracker.dto.ServiceDto;
import com.netcracker.entity.BaseEntity;
import com.netcracker.entity.Service;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
@Component
public class ServiceConverterImpl implements Converter<Service, ServiceDto> {
    @Override
    public ServiceDto toDto(Service entity) {
        List<Integer> list = entity.getEvents().stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toList());

        ServiceDto serviceDto = new ServiceDto(entity.getId(), entity.getName(), entity.getPhone(),
                entity.getPrice(), entity.getDescription(), list);
        return serviceDto;
    }

    @Override
    public Service toEntity(ServiceDto dto) {
        Service service = new Service(dto.getId(), dto.getName(), dto.getPhone(), dto.getPrice(), dto.getDescription());
        return service;
    }
}
