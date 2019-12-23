package com.netcracker.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseEntity {
    private String login;
    private String password;
    private String name;
    private String surname;
    private Role role;
    private int phone;
    private String email;

    public User() {
        super();
    }

    public User(int id, String login, String password, String name, String surname, Role role, int phone, String email) {
        super(id);
        this.login = login;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.phone = phone;
        this.email = email;
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
