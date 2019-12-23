package com.netcracker.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="clients")
@PrimaryKeyJoinColumn(name = "id")
public class Client extends User {
    private double discount;
    @Column(name = "id")
    private int id;
    private int telegram;
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Event> events;
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Comment> comments;
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Request> requests;

    public Client() {
    }

    public Client(int id, String login, String password, String name, String surname, Role role, int phone, String email, double discount, int telegram) {
        super(id, login, password, name, surname, role, phone, email);
        this.discount = discount;
        this.telegram = telegram;
        this.comments = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.events = new ArrayList<>();
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
