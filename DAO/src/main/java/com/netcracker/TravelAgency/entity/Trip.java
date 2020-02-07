package com.netcracker.TravelAgency.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.GregorianCalendar;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "\"Trip\"")
public class Trip extends BaseEntity {
    @NotNull(message = "Arrival date cannot be null")
    @Column(name = "arrivaldate")
    private GregorianCalendar arrivalDate;

    @OneToOne
    private Tour tour;

    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL)
    private List<Tourist> tourists;

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                "\"tour\":" + tour.getId() +
                ", \"arrival date\":" + arrivalDate + '}';
    }
}
