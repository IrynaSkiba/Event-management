package com.netcracker.dto;

import com.netcracker.entity.Service;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ServiceDto extends BaseDto {
    private String name;
    private int phone;
    private BigDecimal price;
    private String description;
    private List<Integer> eventsId;

    public ServiceDto() {
    }

    public ServiceDto(int id, String name, int phone, BigDecimal price, String description, List<Integer> eventsId) {
        super(id);
        this.name = name;
        this.phone = phone;
        this.price = price;
        this.description = description;
        this.eventsId = eventsId;
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
