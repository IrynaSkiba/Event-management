package com.netcracker.service.impl;

import com.netcracker.converter.impl.ClientConverterImpl;
import com.netcracker.dao.repository.ClientRepository;
import com.netcracker.dao.repository.CommentRepository;
import com.netcracker.dao.repository.EventRepository;
import com.netcracker.dao.repository.RequestRepository;
import com.netcracker.dto.ClientDto;
import com.netcracker.entity.Client;
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
public class ClientServiceImplTest {
    @Mock
    ClientRepository clientRepository;
    @Mock
    ClientConverterImpl converter;
    @Mock
    EventRepository eventRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private RequestRepository requestRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private ClientServiceImpl clientService = new ClientServiceImpl(clientRepository, converter, eventRepository, commentRepository,
            requestRepository, passwordEncoder);
    private Client client;

    @Before
    public void setUp() {
        client = new Client();
        client.setId(1);
    }

    @After
    public void tearDown() {
        client = null;
    }

    @Test
    public void getAll() {
        Mockito.when(clientRepository.findAll()).thenReturn(Collections.singletonList(client));
        Assert.assertEquals(Collections.singletonList(converter.toDto(client)), clientService.getAll());
    }

    @Test
    public void get() {
        Optional<Client> optional = Optional.of(client);
        Mockito.when(clientRepository.findById(anyInt())).thenReturn(optional);
        Assert.assertEquals(converter.toDto(optional.get()), clientService.get(anyInt()));
    }

    @Test
    public void create() {
        ClientDto clientDto = new ClientDto();
        List<Integer> requests = new ArrayList<>();
        List<Integer> comments = new ArrayList<>();
        List<Integer> events = new ArrayList<>();
        clientDto.setRequests(requests);
        clientDto.setComments(comments);
        clientDto.setEvents(events);
        Mockito.when(passwordEncoder.encode("pass")).thenReturn("sdksgndjgnsosdjfs;");
        Mockito.when(converter.toEntity(clientDto)).thenReturn(client);
        clientService.create(clientDto);
        Mockito.verify(clientRepository).save(client);
    }

    @Test
    public void delete() {
        Optional<Client> optional = Optional.of(client);
        Mockito.when(clientRepository.findById(anyInt())).thenReturn(optional);
        clientService.delete(client.getId());
        Mockito.verify(clientRepository).delete(client);
    }

    @Test
    public void update() {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(client.getId());
        Optional<Client> optional = Optional.of(client);
        Mockito.when(clientRepository.findById(1)).thenReturn(optional).thenReturn(optional);
        clientService.update(clientDto);
        Mockito.verify(clientRepository, Mockito.times(1)).delete(client);
        Mockito.verify(clientRepository, Mockito.times(1)).save(converter.toEntity(clientDto));
    }

    @Test
    public void idAlreadyExist() {
        Mockito.when(clientRepository.findAll()).thenReturn(Collections.singletonList(client));
        Assert.assertTrue(clientService.idAlreadyExist(1));
    }

    @Test
    public void loginAlreadyExist() {
        client.setLogin("check");
        Mockito.when(clientRepository.findAll()).thenReturn(Collections.singletonList(client));
        Assert.assertTrue(clientService.loginAlreadyExist("check"));
    }

    @Test
    public void getByLogin() {
        client.setLogin("check");
        Mockito.when(clientRepository.findAll()).thenReturn(Collections.singletonList(client));
        Optional<ClientDto> optional = Optional.ofNullable(converter.toDto(client));
        Assert.assertEquals(clientService.getByLogin("check"), optional);
    }

}
