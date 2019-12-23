package com.netcracker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto extends BaseDto {
    private String login;
    private String password;
    private String name;
    private String surname;
    private RoleDto role;
    private int phone;
    private String email;

    public UserDto() {
    }

    public UserDto(int id, String login, String password, String name,
                   String surname, RoleDto role, int phone, String email) {
        super(id);
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.phone = phone;
        this.email = email;
    }

    public UserDto(String login, String password, RoleDto role) {
        super();
        this.login = login;
        this.password = password;
        this.role = role;
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
