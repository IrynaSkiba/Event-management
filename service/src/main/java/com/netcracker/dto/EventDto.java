package com.netcracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class EventDto extends BaseDto {
    private String name;
    private String description;
    private int eventCategoryId;
    private LocalDateTime date;
    private BigDecimal cost;
    private int clientId;
    private int employeeId;
    private List<Integer> servicesId;

    public EventDto() {
    }

    public EventDto(int id, String name, String description, int eventCategoryId, LocalDateTime date, BigDecimal cost,
                    int clientId, int employeeId, List<Integer> servicesId) {
        super(id);
        this.name = name;
        this.description = description;
        this.eventCategoryId = eventCategoryId;
        this.date = date;
        this.cost = cost;
        this.clientId = clientId;
        this.employeeId = employeeId;
        this.servicesId = servicesId;
    }

    public EventDto(String description, int clientId, int employeeId, int eventCategoryId) {
        this.description = description;
        this.clientId = clientId;
        this.employeeId = employeeId;
        this.eventCategoryId = eventCategoryId;
        servicesId = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                ", \"name\":\"" + name + '\"' +
                ", \"description\":\"" + description + '\"' +
                ", \"eventCategory\":\"" + eventCategoryId + '\"' +
                ", \"date\":" + date +
                ", \"cost\":" + cost +
                '}';
    }
}
