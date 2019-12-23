package com.netcracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ClientDto extends UserDto {
    private double discount;
    private int telegram;
    private List<Integer> events;
    private List<Integer> comments;
    private List<Integer> requests;

    public ClientDto() {
    }

    public ClientDto(int id, String login, String password, String name, String surname,
                     int phone, String email, RoleDto role, double discount, int telegram,
                     List<Integer> events, List<Integer> comments, List<Integer> requests) {
        super(id, login, password, name, surname, role, phone, email);
        this.discount = discount;
        this.telegram = telegram;
        this.events = events;
        this.comments = comments;
        this.requests = requests;
    }

    public ClientDto(String login, String password) {
        super(login, password, RoleDto.CLIENT);
        this.requests = new ArrayList<>();
        this.events = new ArrayList<>();
        this.comments = new ArrayList<>();
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
                ", \"discount\":" + discount +
                ", \"telegram\":" + telegram +
                '}';
    }
}
