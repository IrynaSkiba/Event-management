package com.netcracker.converter.impl;

import com.netcracker.converter.Converter;
import com.netcracker.dto.RoleDto;
import com.netcracker.dto.UserDto;
import com.netcracker.entity.Role;
import com.netcracker.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverterImpl implements Converter<User, UserDto> {
    @Override
    public UserDto toDto(User entity) {
        UserDto userDto = new UserDto(entity.getId(), entity.getLogin(), entity.getPassword(),
                entity.getName(), entity.getSurname(), RoleDto.valueOf(entity.getRole().name()),
                entity.getPhone(), entity.getEmail());
        return userDto;
    }

    @Override
    public User toEntity(UserDto dto) {
        User user = new User(dto.getId(), dto.getLogin(), dto.getPassword(), dto.getName(), dto.getSurname(),
                Role.valueOf(dto.getRole().name()), dto.getPhone(), dto.getEmail());
        return user;
    }
}
