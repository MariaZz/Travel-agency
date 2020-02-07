package com.netcracker.TravelAgency.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.GregorianCalendar;

@Getter
@Setter
@Entity
@Table(name = "\"ArrivalDate\"")
public class ArrivalDate extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    @NotNull(message = "Country cannot be null")
    private Country country;

    @NotNull(message = "Arrival date cannot be null")
    @Column(name = "arrivaldate")
    private GregorianCalendar arrivalDate;

    @NotNull(message = "Number of free places cannot be null")
    @Column(name = "numberoffreeplaces")
    private Integer numberOfFreePlaces;

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                "\"country\":" + country.getId() +
                ", \"arrival date\":" + arrivalDate +
                ", \"number of free places\":" + numberOfFreePlaces + '}';
    }
}
