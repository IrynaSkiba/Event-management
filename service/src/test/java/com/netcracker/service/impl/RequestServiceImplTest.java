package com.netcracker.service.impl;

import com.netcracker.converter.impl.RequestConverterImpl;
import com.netcracker.dao.repository.ClientRepository;
import com.netcracker.dao.repository.RequestRepository;
import com.netcracker.dto.RequestDto;
import com.netcracker.entity.Client;
import com.netcracker.entity.Request;
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

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Matchers.anyInt;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class RequestServiceImplTest {
    @Mock
    private RequestRepository requestRepository;
    @Mock
    private RequestConverterImpl converter;
    @Mock
    private ClientRepository clientRepository;
    @InjectMocks
    private RequestServiceImpl requestService = new RequestServiceImpl(requestRepository, converter, clientRepository);

    private Request request;

    @Before
    public void setUp() {
        request = new Request();
        request.setId(1);
    }

    @After
    public void tearDown() {
        request = null;
    }

    @Test
    public void getAll() {
        Mockito.when(requestRepository.findAll()).thenReturn(Collections.singletonList(request));
        Assert.assertEquals(Collections.singletonList(converter.toDto(request)), requestService.getAll());
    }

    @Test
    public void get() {
        Optional<Request> optional = Optional.of(request);
        Mockito.when(requestRepository.findById(anyInt())).thenReturn(optional);
        Assert.assertEquals(converter.toDto(optional.get()), requestService.get(anyInt()));
    }

    @Test
    public void create() {
        RequestDto requestDto = new RequestDto();
        requestDto.setId(request.getId());
        Client client = new Client();
        client.setId(1);
        requestDto.setClientId(client.getId());

        Mockito.when(clientRepository.findById(1)).thenReturn(Optional.of(client)).thenReturn(Optional.of(client));
        Mockito.when(converter.toEntity(requestDto)).thenReturn(request);
        requestService.create(requestDto);
        Mockito.verify(requestRepository).save(request);
    }

    @Test
    public void delete() {
        Optional<Request> optional = Optional.of(request);
        Mockito.when(requestRepository.findById(anyInt())).thenReturn(optional);
        requestService.delete(request.getId());
        Mockito.verify(requestRepository).delete(request);
    }

    @Test
    public void update() {
        RequestDto requestDto = new RequestDto();
        requestDto.setId(request.getId());
        Optional<Request> optional = Optional.of(request);
        Mockito.when(requestRepository.findById(1)).thenReturn(optional).thenReturn(optional);
        requestService.update(requestDto);
        Mockito.verify(requestRepository, Mockito.times(1)).delete(request);
        Mockito.verify(requestRepository, Mockito.times(1)).save(converter.toEntity(requestDto));
    }

    @Test
    public void idAlreadyExist() {
        Mockito.when(requestRepository.findAll()).thenReturn(Collections.singletonList(request));
        Assert.assertTrue(requestService.idAlreadyExist(1));
    }
}
