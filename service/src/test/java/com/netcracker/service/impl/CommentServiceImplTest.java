package com.netcracker.service.impl;

import com.netcracker.converter.impl.CommentConverterImpl;
import com.netcracker.dao.repository.ClientRepository;
import com.netcracker.dao.repository.CommentRepository;
import com.netcracker.dao.repository.ServiceRepository;
import com.netcracker.dto.CommentDto;
import com.netcracker.entity.Client;
import com.netcracker.entity.Comment;
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

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Matchers.anyInt;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentConverterImpl converter;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private ServiceRepository serviceRepository;
    @InjectMocks
    private CommentServiceImpl commentService = new CommentServiceImpl(commentRepository, converter,
            clientRepository, serviceRepository);
    private Comment comment;

    @Before
    public void setUp() {
        comment = new Comment();
        comment.setId(1);
    }

    @After
    public void tearDown() {
        comment = null;
    }

    @Test
    public void getAll() {
        Mockito.when(commentRepository.findAll()).thenReturn(Collections.singletonList(comment));
        Assert.assertEquals(Collections.singletonList(converter.toDto(comment)), commentService.getAll());
    }

    @Test
    public void get() {
        Optional<Comment> optional = Optional.of(comment);
        Mockito.when(commentRepository.findById(anyInt())).thenReturn(optional);
        Assert.assertEquals(converter.toDto(optional.get()), commentService.get(anyInt()));
    }

    @Test
    public void create() {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        Client client = new Client();
        client.setId(1);
        Service service = new Service();
        service.setId(1);
        commentDto.setClientId(client.getId());
        commentDto.setServiceId(service.getId());

        Mockito.when(converter.toEntity(commentDto)).thenReturn(comment);
        Mockito.when(clientRepository.findById(1)).thenReturn(Optional.of(client)).thenReturn(Optional.of(client));
        Mockito.when(serviceRepository.findById(1)).thenReturn(Optional.of(service)).thenReturn(Optional.of(service));
        commentService.create(commentDto);
        Mockito.verify(commentRepository).save(comment);
    }

    @Test
    public void delete() {
        Optional<Comment> optional = Optional.of(comment);
        Mockito.when(commentRepository.findById(anyInt())).thenReturn(optional);
        commentService.delete(comment.getId());
        Mockito.verify(commentRepository).delete(comment);
    }

    @Test
    public void update() {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        Optional<Comment> optional = Optional.of(comment);
        Mockito.when(commentRepository.findById(1)).thenReturn(optional).thenReturn(optional);
        commentService.update(commentDto);
        Mockito.verify(commentRepository, Mockito.times(1)).delete(comment);
        Mockito.verify(commentRepository, Mockito.times(1)).save(converter.toEntity(commentDto));
    }

    @Test
    public void idAlreadyExist() {
        Mockito.when(commentRepository.findAll()).thenReturn(Collections.singletonList(comment));
        Assert.assertTrue(commentService.idAlreadyExist(1));
    }

    @Test
    public void commentAlreadyExist() {
        Client client = new Client();
        client.setId(1);
        Service service = new Service();
        service.setId(1);
        comment.setClient(client);
        comment.setService(service);
        Mockito.when(commentRepository.findAll()).thenReturn(Collections.singletonList(comment));
        Assert.assertTrue(commentService.commentAlreadyExist(client.getId(), service.getId()));
    }
}
