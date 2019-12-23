package com.netcracker.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "events")
public class Event extends BaseEntity {
    private String name;
    private String description;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "eventCategory_id")
    private EventCategory eventCategory;
    private LocalDateTime date;
    private BigDecimal cost;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private  Client client;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private  Employee employee;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "event_to_service",
            joinColumns = {@JoinColumn(name = "event_id")},
            inverseJoinColumns = {@JoinColumn(name = "service_id")}
    )
    private List<Service> services;

    public Event() {

    }

    public Event(int id, String name, String description, LocalDateTime date, BigDecimal cost) {
        super(id);
        this.name = name;
        this.description = description;
        this.date = date;
        this.cost = cost;
        this.services = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                ", \"name\":\"" + name + '\"' +
                ", \"description\":\"" + description + '\"' +
                ", \"eventCategory\":\"" + eventCategory.getName() + '\"' +
                ", \"date\":" + date +
                ", \"cost\":" + cost +
                '}';
    }
}
