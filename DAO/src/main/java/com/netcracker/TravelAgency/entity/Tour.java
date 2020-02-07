package com.netcracker.TravelAgency.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "\"Tour\"")
public class Tour extends BaseEntity {
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Tour_ArrivalDate",
            joinColumns = {@JoinColumn(name = "tour_id")},
            inverseJoinColumns = {@JoinColumn(name = "arrivaldate_id")}
    )
    @NotNull(message = "Arrival dates cannot be null")
    @Column(name = "arrivaldates")
    private List<ArrivalDate> arrivalDates;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    @NotNull(message = "Country cannot be null")
    private Country country;

    @NotNull(message = "Length of stay cannot be null")
    @Size(min = 1, max = 30, message = "Length of stay must be between 1 and 30 days")
    @Column(name = "lengthofstay")
    private Integer lengthOfStay;

    @NotNull(message = "Hotel category cannot be null")
    private Hotel.HotelCategory hotelCategory;

    @NotNull(message = "Type of food cannot be null")
    private Hotel.TypeOfFood typeOfFood;

    @ManyToOne
    private Operator operator ;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    @NotNull(message = "Hotel cannot be null")
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @NotNull(message = "Room cannot be null")
    private Room room;

    @NotNull(message = "Cost cannot be null")
    @Column(name = "cost")
    private Integer cost;
    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                "\"country\":" + country.getId() +
                ", \"length of stay\":" + lengthOfStay +
                ", \"hotel\":" + hotel.getId() +
                ", \"hotel category\":" + hotelCategory.toString() +
                ", \"type of food\":" + typeOfFood.toString() +
                "\"room\":" + room.getId() +
                ", \"cost\":" + cost + '}';
    }
}
