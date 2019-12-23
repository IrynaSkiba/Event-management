package com.netcracker.service.impl;

import com.netcracker.converter.impl.EventConverterImpl;
import com.netcracker.dao.repository.*;
import com.netcracker.dto.EventCategoryDto;
import com.netcracker.dto.EventDto;
import com.netcracker.entity.*;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.anyInt;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class EventServiceImplTest {
    @Mock
    private EventRepository eventRepository;
    @Mock
    private EventConverterImpl converter;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private ServiceRepository serviceRepository;
    @Mock
    private EventCategoryRepository categoryRepository;
    @Mock
    private RequestRepository requestRepository;
    @InjectMocks
    private EventServiceImpl eventService = new EventServiceImpl(eventRepository, converter, clientRepository,
            employeeRepository, serviceRepository, categoryRepository, requestRepository);
    private Event event;

    @Before
    public void setUp() {
        event = new Event();
        event.setId(1);
    }

    @After
    public void tearDown(){
        event = null;
    }

    @Test
    public void getAll() {
        Mockito.when(eventRepository.findAll()).thenReturn(Collections.singletonList(event));
        Assert.assertEquals(Collections.singletonList(converter.toDto(event)), eventService.getAll());
    }

    @Test
    public void get() {
        Optional<Event> optional = Optional.of(event);
        Mockito.when(eventRepository.findById(anyInt())).thenReturn(optional);
        Assert.assertEquals(converter.toDto(optional.get()), eventService.get(anyInt()));
    }

    @Test
    public void create() {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        Client client = new Client();
        client.setId(1);
        Employee employee = new Employee();
        employee.setId(1);
        EventCategory eventCategory = new EventCategory();
        eventCategory.setId(1);
        Service service = new Service();
        service.setId(1);
        List<Integer> services = new ArrayList<>();
        services.add(service.getId());
        eventDto.setClientId(client.getId());
        eventDto.setEmployeeId(employee.getId());
        eventDto.setEventCategoryId(eventCategory.getId());
        eventDto.setServicesId(services);

        Mockito.when(converter.toEntity(eventDto)).thenReturn(event);
        Mockito.when(clientRepository.findById(1)).thenReturn(Optional.of(client));
        Mockito.when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(employee));
        Mockito.when(categoryRepository.findById(anyInt())).thenReturn(Optional.of(eventCategory));
        Mockito.when(serviceRepository.findById(anyInt())).thenReturn(Optional.of(service));
        eventService.create(eventDto);
        Mockito.verify(eventRepository).save(event);
    }

    @Test
    public void delete() {
        Optional<Event> optional = Optional.of(event);
        Mockito.when(eventRepository.findById(anyInt())).thenReturn(optional);
        eventService.delete(event.getId());
        Mockito.verify(eventRepository).delete(event);
    }

    @Test
    public void update() {
        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        Optional<Event> optional = Optional.of(event);
        Mockito.when(eventRepository.findById(1)).thenReturn(optional).thenReturn(optional);
        eventService.update(eventDto);
        Mockito.verify(eventRepository, Mockito.times(1)).delete(event);
        Mockito.verify(eventRepository, Mockito.times(1)).save(converter.toEntity(eventDto));
    }

    @Test
    public void idAlreadyExist() {
        Mockito.when(eventRepository.findAll()).thenReturn(Collections.singletonList(event));
        Assert.assertTrue(eventService.idAlreadyExist(1));
    }
}
