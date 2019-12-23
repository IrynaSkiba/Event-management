package com.netcracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EmployeeDto extends UserDto {
    private List<Integer> eventsId;

    public EmployeeDto() {
    }

    public EmployeeDto(int id, String login, String password, String name, String surname, RoleDto roleDto,
                       int phone, String email, List<Integer> eventsId) {
        super(id, login, password, name, surname, roleDto, phone, email);
        this.eventsId = eventsId;
    }

    public EmployeeDto(String login, String password) {
        super(login, password, RoleDto.EMPLOYEE);
        eventsId = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                ", \"role\":\"" + getRole() + '\"' +
                ", \"login\":\"" + getLogin() + '\"' +
                ", \"password\":\"" + getPassword() + '\"' +
                ", \"name\":\"" + getName() + '\"' +
                ", \"surname\":\"" + getSurname() + '\"' +
                ", \"phone\":" + getPhone() +
                ", \"email\":\"" + getEmail() + '\"' +
                '}';
    }
}
