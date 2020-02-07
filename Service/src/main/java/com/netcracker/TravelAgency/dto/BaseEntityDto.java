package com.netcracker.TravelAgency.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
public abstract class BaseEntityDto {
    private int id;

    public BaseEntityDto(int id) {
        this.id = id;
    }

    public BaseEntityDto() {
        Random random = new Random();
        id = random.nextInt(2147483647);
    }
}