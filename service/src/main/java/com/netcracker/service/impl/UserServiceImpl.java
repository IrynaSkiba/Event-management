package com.netcracker.service.impl;

import com.netcracker.converter.impl.UserConverterImpl;
import com.netcracker.dao.repository.UserRepository;
import com.netcracker.dto.UserDto;
import com.netcracker.entity.User;
import com.netcracker.exceptions.BadRequestException;
import com.netcracker.exceptions.NotFoundException;
import com.netcracker.service.CrudService;
import com.netcracker.service.UserService;
import com.netcracker.service.ValidId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements CrudService<UserDto>, ValidId, UserService, UserDetailsService {
    private UserRepository userRepository;
    private UserConverterImpl converter;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserConverterImpl converter,
                           BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.converter = converter;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void create(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = converter.toEntity(userDto);
        userRepository.save(user);
    }

    @Override
    public UserDto get(int id) {
        UserDto userDto;
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (userRepository.findById(id).isPresent()) {
            User user = userRepository.findById(id).get();
            userDto = converter.toDto(user);
        } else {
            throw new NotFoundException("User not found");
        }
        return userDto;
    }

    @Override
    public List<UserDto> getAll() {
        List<UserDto> list = userRepository.findAll().stream()
                .map(x -> converter.toDto(x))
                .collect(Collectors.toList());
        return list;

    }

    @Override
    public void update(UserDto userDto) {
        if (userDto.getId() <= 0) throw new BadRequestException("Not valid data");
        if (userRepository.findById(userDto.getId()).isPresent()) {
            userRepository.delete(userRepository.findById(userDto.getId()).get());
            userRepository.save(converter.toEntity(userDto));
        } else {
            throw new NotFoundException("User doesn't exist");
        }
    }

    @Override
    public void delete(int id) {
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (userRepository.findById(id).isPresent()) {
            userRepository.delete(userRepository.findById(id).get());
        } else {
            throw new NotFoundException("User doesn't exist");
        }
    }

    @Override
    public Optional<UserDto> getByLogin(String login) {
        Optional<User> user = userRepository.findAll().stream()
                .filter(x -> x.getLogin().equals(login))
                .findFirst();
        UserDto userDto = null;
        if (user.isPresent()) userDto = converter.toDto(user.get());
        return Optional.ofNullable(userDto);
    }

    @Override
    public boolean loginAlreadyExist(String login) {
        return userRepository.findAll().stream().anyMatch(l -> l.getLogin().equals(login));
    }

    @Override
    public boolean idAlreadyExist(int id) {
        return userRepository.findAll().stream().anyMatch(l -> l.getId() == id);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(s);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), grantedAuthorities);
    }
}
