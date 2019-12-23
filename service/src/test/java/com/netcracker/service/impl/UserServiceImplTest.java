package com.netcracker.service.impl;

import com.netcracker.converter.impl.UserConverterImpl;
import com.netcracker.dao.repository.UserRepository;
import com.netcracker.dto.UserDto;
import com.netcracker.entity.User;
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
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserConverterImpl converter;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private UserServiceImpl userService = new UserServiceImpl(userRepository, converter, passwordEncoder);

    private User user;

    @Before
    public void setUp() {
        user = new User();
        user.setId(1);
    }

    @After
    public void tearDown() {
        user = null;
    }

    @Test
    public void getAll() {
        Mockito.when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        Assert.assertEquals(Collections.singletonList(converter.toDto(user)), userService.getAll());
    }

    @Test
    public void get() {
        Optional<User> optional = Optional.of(user);
        Mockito.when(userRepository.findById(anyInt())).thenReturn(optional);
        Assert.assertEquals(converter.toDto(optional.get()), userService.get(anyInt()));
    }

    @Test
    public void create() {
        UserDto userDto = new UserDto();
        Mockito.when(passwordEncoder.encode("pass")).thenReturn("sdksgndjgnsosdjfs;");
        Mockito.when(converter.toEntity(userDto)).thenReturn(user);
        userService.create(userDto);
        Mockito.verify(userRepository).save(user);
    }

    @Test
    public void delete() {
        Optional<User> optional = Optional.of(user);
        Mockito.when(userRepository.findById(anyInt())).thenReturn(optional);
        userService.delete(user.getId());
        Mockito.verify(userRepository).delete(user);
    }

    @Test
    public void update() {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        Optional<User> optional = Optional.of(user);
        Mockito.when(userRepository.findById(1)).thenReturn(optional).thenReturn(optional);
        userService.update(userDto);
        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
        Mockito.verify(userRepository, Mockito.times(1)).save(converter.toEntity(userDto));
    }

    @Test
    public void idAlreadyExist() {
        Mockito.when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        Assert.assertTrue(userService.idAlreadyExist(1));
    }

    @Test
    public void loginAlreadyExist() {
        user.setLogin("check");
        Mockito.when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        Assert.assertTrue(userService.loginAlreadyExist("check"));
    }

    @Test
    public void getByLogin() {
        user.setLogin("check");
        Mockito.when(userRepository.findAll()).thenReturn(Collections.singletonList(user));
        Optional<UserDto> optional = Optional.ofNullable(converter.toDto(user));
        Assert.assertEquals(userService.getByLogin("check"), optional);
    }
}
