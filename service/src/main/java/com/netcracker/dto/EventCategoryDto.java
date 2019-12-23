package com.netcracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EventCategoryDto extends BaseDto {
    private CategoryDto name;
    private BigDecimal price;

    public EventCategoryDto() {
    }

    public EventCategoryDto(int id, BigDecimal price) {
        super(id);
        this.price = price;
    }

    public EventCategoryDto(int id, CategoryDto category, BigDecimal price) {
        super(id);
        this.name = category;
        this.price = price;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                ", \"name\":\"" + name + '\"' +
                ", \"price\":" + price +
                '}';
    }
}
