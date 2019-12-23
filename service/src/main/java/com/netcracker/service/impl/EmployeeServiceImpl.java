package com.netcracker.service.impl;

import com.netcracker.converter.impl.EmployeeConverterImpl;
import com.netcracker.converter.impl.EventConverterImpl;
import com.netcracker.dao.repository.EmployeeRepository;
import com.netcracker.dao.repository.EventRepository;
import com.netcracker.dto.EmployeeDto;
import com.netcracker.dto.EventDto;
import com.netcracker.dto.RoleDto;
import com.netcracker.entity.Employee;
import com.netcracker.entity.Event;
import com.netcracker.exceptions.BadRequestException;
import com.netcracker.exceptions.NotFoundException;
import com.netcracker.service.CrudService;
import com.netcracker.service.EmployeeService;
import com.netcracker.service.ValidId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements CrudService<EmployeeDto>, ValidId, EmployeeService {
    private EmployeeRepository employeeRepository;
    private EmployeeConverterImpl converter;
    private EventRepository eventRepository;
    private EventConverterImpl eventConverter;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeConverterImpl converter,
                               EventRepository eventRepository, EventConverterImpl eventConverter,
                               BCryptPasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.converter = converter;
        this.eventRepository = eventRepository;
        this.eventConverter = eventConverter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void create(EmployeeDto employeeDto) {
        if (employeeDto.getPhone() < 0)
            throw new BadRequestException("Not valid data");
        List<Event> events = employeeDto.getEventsId().stream()
                .map(x -> eventRepository.findById(x).get())
                .collect(Collectors.toList());
        employeeDto.setPassword(passwordEncoder.encode(employeeDto.getPassword()));

        Employee employee = converter.toEntity(employeeDto);
        employee.setEvents(events);
        employeeRepository.save(employee);
    }

    @Override
    public EmployeeDto get(int id) {
        EmployeeDto employeeDto;
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (employeeRepository.findById(id).isPresent()) {
            Employee employee = employeeRepository.findById(id).get();
            employeeDto = converter.toDto(employee);
        } else {
            throw new NotFoundException("Employee not found");
        }
        return employeeDto;
    }

    @Override
    public List<EmployeeDto> getAll() {
        List<EmployeeDto> list = employeeRepository.findAll().stream()
                .map(x -> converter.toDto(x))
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public void update(EmployeeDto employeeDto) {
        if (employeeDto.getPhone() < 0 || employeeDto.getId() <= 0 || employeeDto.getRole() != RoleDto.EMPLOYEE)
            throw new BadRequestException("Not valid data");
        if (employeeRepository.findById(employeeDto.getId()).isPresent()) {
            employeeRepository.delete(employeeRepository.findById(employeeDto.getId()).get());
            employeeDto.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
            employeeRepository.save(converter.toEntity(employeeDto));
        } else {
            throw new NotFoundException("Employee doesn't exist");
        }
    }

    @Override
    public void delete(int id) {
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (employeeRepository.findById(id).isPresent()) {
            employeeRepository.delete(employeeRepository.findById(id).get());
        } else {
            throw new NotFoundException("Employee doesn't exist");
        }
    }

    @Override
    public boolean idAlreadyExist(int id) {
        return employeeRepository.findAll().stream().anyMatch(l -> l.getId() == id);
    }

    @Override
    public Optional<EmployeeDto> getByLogin(String login) {
        Optional<Employee> employee = employeeRepository.findAll().stream()
                .filter(x -> x.getLogin().equals(login))
                .findFirst();
        EmployeeDto employeeDto = null;
        if (employee.isPresent()) employeeDto = converter.toDto(employee.get());
        return Optional.ofNullable(employeeDto);
    }

    @Override
    public boolean loginAlreadyExist(String login) {
        return employeeRepository.findAll().stream().anyMatch(l -> l.getLogin().equals(login));
    }

    @Override
    public List<EventDto> getEvents(int id) {
        List<EventDto> list = employeeRepository.findById(id).get().getEvents().stream()
                .map(x -> eventConverter.toDto(eventRepository.findById(x.getId()).get()))
                .collect(Collectors.toList());
        return list;
    }

}
