package com.netcracker.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "services")
public class Service extends BaseEntity {
    private String name;
    private int phone;
    private BigDecimal price;
    private String description;
    @ManyToMany(mappedBy = "services")
    private List<Event> events;
    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Service() {
    }

    public Service(int id) {
        super(id);
    }

    public Service(String name, int phone, BigDecimal price, String description) {
        super();
        this.name = name;
        this.phone = phone;
        this.price = price;
        this.description = description;
        this.events = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public Service(int id, String name, int phone, BigDecimal price, String description) {
        super(id);
        this.name = name;
        this.phone = phone;
        this.price = price;
        this.description = description;
        this.events = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                ", \"name\":\"" + name + '\"' +
                ", \"description\":\"" + description + '\"' +
                ", \"phone\":" + phone +
                ", \"price\":" + price +
                '}';
    }
}
