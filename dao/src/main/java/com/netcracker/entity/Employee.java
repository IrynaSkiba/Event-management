package com.netcracker.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "employees")
@PrimaryKeyJoinColumn(name = "id")
public class Employee extends User {
    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Event> events;

    @Column(name = "id")
    private int id;

    public Employee() {
    }

    public Employee(int id, String login, String password, String name, String surname, int phone, String email) {
        super(id, login, password, name, surname, Role.EMPLOYEE, phone, email);
        super.setRole(Role.EMPLOYEE);
        events = new ArrayList<>();
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
