package com.netcracker.service.impl;

import com.netcracker.converter.impl.ServiceConverterImpl;
import com.netcracker.dao.repository.EventRepository;
import com.netcracker.dao.repository.ServiceRepository;
import com.netcracker.dto.ServiceDto;
import com.netcracker.entity.Event;
import com.netcracker.entity.Service;
import com.netcracker.exceptions.BadRequestException;
import com.netcracker.exceptions.NotFoundException;
import com.netcracker.service.CrudService;
import com.netcracker.service.ValidId;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements CrudService<ServiceDto>, ValidId {
    private ServiceRepository serviceRepository;
    private ServiceConverterImpl converter;
    private EventRepository eventRepository;

    @Autowired
    public ServiceServiceImpl(ServiceRepository serviceRepository, ServiceConverterImpl converter,
                              EventRepository eventRepository) {
        this.serviceRepository = serviceRepository;
        this.converter = converter;
        this.eventRepository = eventRepository;
    }

    @Override
    public void create(ServiceDto serviceDto) {
        Service service = converter.toEntity(serviceDto);

        List<Event> events = serviceDto.getEventsId().stream()
                .map(x -> eventRepository.findById(x).get())
                .collect(Collectors.toList());
        service.setEvents(events);
        serviceRepository.save(service);
    }

    @Override
    public ServiceDto get(int id) {
        ServiceDto serviceDto;
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (serviceRepository.findById(id).isPresent()) {
            Service service = serviceRepository.findById(id).get();
            serviceDto = converter.toDto(service);
        } else {
            throw new NotFoundException("Service not found");
        }
        return serviceDto;
    }

    @Override
    public List<ServiceDto> getAll() {
        List<ServiceDto> list = serviceRepository.findAll().stream()
                .map(x -> converter.toDto(x))
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public void update(ServiceDto serviceDto) {
        if (serviceDto.getId() <= 0) throw new BadRequestException("Not valid data");
        if (serviceRepository.findById(serviceDto.getId()).isPresent()) {
            serviceRepository.delete(serviceRepository.findById(serviceDto.getId()).get());
            serviceRepository.save(converter.toEntity(serviceDto));
        } else {
            throw new NotFoundException("Service doesn't exist");
        }
    }

    @Override
    public void delete(int id) {
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (serviceRepository.findById(id).isPresent()) {
            serviceRepository.delete(serviceRepository.findById(id).get());
        } else {
            throw new NotFoundException("Service doesn't exist");
        }
    }

    @Override
    public boolean idAlreadyExist(int id) {
        return serviceRepository.findAll().stream().anyMatch(l -> l.getId() == id);
    }
}
