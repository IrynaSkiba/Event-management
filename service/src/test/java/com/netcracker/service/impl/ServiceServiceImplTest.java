package com.netcracker.service.impl;

import com.netcracker.converter.impl.ServiceConverterImpl;
import com.netcracker.dao.repository.EventRepository;
import com.netcracker.dao.repository.ServiceRepository;
import com.netcracker.dto.ServiceDto;
import com.netcracker.entity.Service;
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
public class ServiceServiceImplTest {
    @Mock
    private ServiceRepository serviceRepository;
    @Mock
    private ServiceConverterImpl converter;
    @Mock
    private EventRepository eventRepository;
    @InjectMocks
    private ServiceServiceImpl serviceService = new ServiceServiceImpl(serviceRepository, converter, eventRepository);

    private Service service;

    @Before
    public void setUp() {
        service = new Service();
        service.setId(1);
    }

    @After
    public void tearDown() {
        service = null;
    }

    @Test
    public void getAll() {
        Mockito.when(serviceRepository.findAll()).thenReturn(Collections.singletonList(service));
        Assert.assertEquals(Collections.singletonList(converter.toDto(service)), serviceService.getAll());
    }

    @Test
    public void get() {
        Optional<Service> optional = Optional.of(service);
        Mockito.when(serviceRepository.findById(anyInt())).thenReturn(optional);
        Assert.assertEquals(converter.toDto(optional.get()), serviceService.get(anyInt()));
    }

    @Test
    public void create() {
        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setId(service.getId());
        List<Integer> events = new ArrayList<>();
        serviceDto.setEventsId(events);

        Mockito.when(converter.toEntity(serviceDto)).thenReturn(service);
//        Mockito.when(clientRepository.findById(1)).thenReturn(Optional.of(client)).thenReturn(Optional.of(client));
//        Mockito.when(serviceRepository.findById(1)).thenReturn(Optional.of(service)).thenReturn(Optional.of(service));
        serviceService.create(serviceDto);
        Mockito.verify(serviceRepository).save(service);
    }

    @Test
    public void delete() {
        Optional<Service> optional = Optional.of(service);
        Mockito.when(serviceRepository.findById(anyInt())).thenReturn(optional);
        serviceService.delete(service.getId());
        Mockito.verify(serviceRepository).delete(service);
    }

    @Test
    public void update() {
        ServiceDto serviceDto = new ServiceDto();
        serviceDto.setId(service.getId());
        Optional<Service> optional = Optional.of(service);
        Mockito.when(serviceRepository.findById(1)).thenReturn(optional).thenReturn(optional);
        serviceService.update(serviceDto);
        Mockito.verify(serviceRepository, Mockito.times(1)).delete(service);
        Mockito.verify(serviceRepository, Mockito.times(1)).save(converter.toEntity(serviceDto));
    }

    @Test
    public void idAlreadyExist() {
        Mockito.when(serviceRepository.findAll()).thenReturn(Collections.singletonList(service));
        Assert.assertTrue(serviceService.idAlreadyExist(1));
    }

}
