package com.netcracker.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "events_categories")
public class EventCategory extends BaseEntity {
    private Category name;
    private BigDecimal price;

    public EventCategory() {
    }

    public EventCategory(int id, Category category, BigDecimal price) {
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
