package com.netcracker.service.impl;

import com.netcracker.converter.impl.EventConverterImpl;
import com.netcracker.dao.repository.*;
import com.netcracker.dto.EventDto;
import com.netcracker.entity.Event;
import com.netcracker.entity.Service;
import com.netcracker.exceptions.BadRequestException;
import com.netcracker.exceptions.NotFoundException;
import com.netcracker.service.CrudService;
import com.netcracker.service.EventService;
import com.netcracker.service.ValidId;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class EventServiceImpl implements CrudService<EventDto>, ValidId, EventService {
    private EventRepository eventRepository;
    private EventConverterImpl converter;
    private ClientRepository clientRepository;
    private EmployeeRepository employeeRepository;
    private ServiceRepository serviceRepository;
    private EventCategoryRepository eventCategoryRepository;
    private RequestRepository requestRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, EventConverterImpl converter, ClientRepository clientRepository,
                            EmployeeRepository employeeRepository, ServiceRepository serviceRepository,
                            EventCategoryRepository eventCategoryRepository, RequestRepository requestRepository) {
        this.eventRepository = eventRepository;
        this.converter = converter;
        this.clientRepository = clientRepository;
        this.employeeRepository = employeeRepository;
        this.serviceRepository = serviceRepository;
        this.eventCategoryRepository = eventCategoryRepository;
        this.requestRepository = requestRepository;
    }

    @Override
    public void create(EventDto eventDto) {
        Event event = converter.toEntity(eventDto);

        event.setClient(clientRepository.findById(eventDto.getClientId()).get());
        event.setEmployee(employeeRepository.findById(eventDto.getEmployeeId()).get());
        event.setEventCategory(eventCategoryRepository.findById(eventDto.getEventCategoryId()).get());
        List<Service> list = eventDto.getServicesId().stream()
                .map(x -> serviceRepository.findById(x).get())
                .collect(Collectors.toList());
        event.setServices(list);
        eventRepository.save(event);
    }

    @Override
    public EventDto get(int id) {
        EventDto eventDto;
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (eventRepository.findById(id).isPresent()) {
            Event event = eventRepository.findById(id).get();
            eventDto = converter.toDto(event);
        } else {
            throw new NotFoundException("Event not found");
        }
        return eventDto;
    }

    @Override
    public List<EventDto> getAll() {
        List<EventDto> list = eventRepository.findAll().stream()
                .map(x -> converter.toDto(x))
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public void update(EventDto eventDto) {
        if (eventDto.getId() <= 0) throw new BadRequestException("Not valid data");
        if (eventRepository.findById(eventDto.getId()).isPresent()) {
            eventRepository.delete(eventRepository.findById(eventDto.getId()).get());
            eventRepository.save(converter.toEntity(eventDto));
        } else {
            throw new NotFoundException("Event doesn't exist");
        }
    }

    @Override
    public void delete(int id) {
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (eventRepository.findById(id).isPresent()) {
            eventRepository.delete(eventRepository.findById(id).get());
        } else {
            throw new NotFoundException("Event doesn't exist");
        }
    }

    @Override
    public boolean idAlreadyExist(int id) {
        return eventRepository.findAll().stream().anyMatch(l -> l.getId() == id);
    }

    @Override
    public void setName(String name, int id) {
        eventRepository.findById(id).get().setName(name);
    }

//    @Override
//    public void createEventFromRequest(RequestDto requestDto, EmployeeDto employeeDto, EventCategoryDto eventCategoryDto) {
//        if (clientRepository.findById(requestDto.getClientId()).isPresent() &&
//                employeeRepository.findById(employeeDto.getId()).isPresent() &&
//                eventCategoryRepository.findById(eventCategoryDto.getId()).isPresent()) {
//            EventDto eventDto = new EventDto(requestDto.getComment(), requestDto.getClientId(),
//                    employeeDto.getId(), eventCategoryDto.getId());
//
//            Event event = converter.toEntity(eventDto);
//
//            event.setClient(clientRepository.findById(eventDto.getClientId()).get());
//            event.setEmployee(employeeRepository.findById(eventDto.getEmployeeId()).get());
//            event.setEventCategory(eventCategoryRepository.findById(eventDto.getEventCategoryId()).get());
//
//            eventRepository.save(event);
//            requestRepository.findById(requestDto.getId()).get().setStatus(RequestStatus.PROCESSED);
//        }
//    }

    @Override
    public void setDescription(String content, int num) {
        eventRepository.findById(num).get().setDescription(content);
    }

    @Override
    public void setCost(BigDecimal cost, int num) {
        eventRepository.findById(num).get().setCost(cost);
    }
}
