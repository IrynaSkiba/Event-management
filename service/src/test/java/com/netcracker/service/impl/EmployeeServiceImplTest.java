package com.netcracker.service.impl;

import com.netcracker.converter.impl.EmployeeConverterImpl;
import com.netcracker.converter.impl.EventConverterImpl;
import com.netcracker.dao.repository.EmployeeRepository;
import com.netcracker.dao.repository.EventRepository;
import com.netcracker.dto.EmployeeDto;
import com.netcracker.entity.Employee;
import com.netcracker.entity.Event;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.anyInt;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceImplTest {
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeConverterImpl converter;
    @Mock
    private EventRepository eventRepository;
    @Mock
    private EventConverterImpl eventConverter;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private EmployeeServiceImpl employeeService = new EmployeeServiceImpl(employeeRepository, converter,
            eventRepository, eventConverter, passwordEncoder);

    private Employee employee;

    @Before
    public void setUp() {
        employee = new Employee();
        employee.setId(1);
    }

    @After
    public void tearDown() {
        employee = null;
    }

    @Test
    public void getAll() {
        Mockito.when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));
        Assert.assertEquals(Collections.singletonList(converter.toDto(employee)), employeeService.getAll());
    }

    @Test
    public void get() {
        Optional<Employee> optional = Optional.of(employee);
        Mockito.when(employeeRepository.findById(anyInt())).thenReturn(optional).thenReturn(optional);
        Assert.assertEquals(converter.toDto(optional.get()), employeeService.get(anyInt()));
    }

    @Test
    public void create() {
        EmployeeDto employeeDto = new EmployeeDto();
        List<Integer> events = new ArrayList<>();
        employeeDto.setEventsId(events);
        Mockito.when(passwordEncoder.encode("pass")).thenReturn("sdksgndjgnsosdjfs;");
        Mockito.when(converter.toEntity(employeeDto)).thenReturn(employee);
        employeeService.create(employeeDto);
        Mockito.verify(employeeRepository).save(employee);
    }

    @Test
    public void delete() {
        Optional<Employee> optional = Optional.of(employee);
        Mockito.when(employeeRepository.findById(anyInt())).thenReturn(optional);
        employeeService.delete(employee.getId());
        Mockito.verify(employeeRepository).delete(employee);
    }

    @Test
    public void update() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        Optional<Employee> optional = Optional.of(employee);
        Mockito.when(employeeRepository.findById(1)).thenReturn(optional).thenReturn(optional);
        employeeService.update(employeeDto);
        Mockito.verify(employeeRepository, Mockito.times(1)).delete(employee);
        Mockito.verify(employeeRepository, Mockito.times(1)).save(converter.toEntity(employeeDto));
    }

    @Test
    public void idAlreadyExist() {
        Mockito.when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));
        Assert.assertTrue(employeeService.idAlreadyExist(1));
    }

    @Test
    public void loginAlreadyExist() {
        employee.setLogin("check");
        Mockito.when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));
        Assert.assertTrue(employeeService.loginAlreadyExist("check"));
    }

    @Test
    public void getByLogin() {
        employee.setLogin("check");
        Mockito.when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));
        Optional<EmployeeDto> optional = Optional.ofNullable(converter.toDto(employee));
        Assert.assertEquals(employeeService.getByLogin("check"), optional);
    }

    @Test
    public void getEvents() {
        Event event = new Event();
        event.setId(1);
        List<Event> events = new ArrayList<>();
        events.add(event);
        employee.setEvents(events);
        Mockito.when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));
        Mockito.when(eventRepository.findById(anyInt())).thenReturn(Optional.of(event));
        Assert.assertEquals(Collections.singletonList(eventConverter.toDto(event)),
                employeeService.getEvents(event.getId()));
    }
}
