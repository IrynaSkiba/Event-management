package com.netcracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
public abstract class BaseDto {
    private int id;

    public BaseDto(int id) {
        this.id = id;
    }

    public BaseDto() {
        Random random = new Random();
        id = random.nextInt(2147483647);
    }
}
