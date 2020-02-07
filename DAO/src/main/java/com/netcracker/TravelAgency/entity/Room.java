package com.netcracker.TravelAgency.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "\"Room\"")
public class Room extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    @NotNull(message = "Hotel cannot be null")
    private Hotel hotel;

    @NotNull(message = "Number of sleeping places cannot be null")
    @Size(min = 1, max = 8, message = "Number of sleeping places must be between 1 and 8 characters")
    @Column(name = "numberOfSleepingPlaces")
    private Integer numberOfSleepingPlaces;

    @NotNull(message = "Number of rooms cannot be null")
    @Column(name = "numberofrooms")
    private Integer numberOfRooms;


    @Enumerated
    @NotNull(message = "Type of room cannot be null")
    @Column(name = "typeofroom")
    private TypeOfRoom typeOfRoom;


    public enum TypeOfRoom {
        STANDARD,
        SUITE,
        PRESIDENT_SUITE
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                "\"hotel\":" + hotel.getId() +
                ", \"number of sleeping places\":" + numberOfSleepingPlaces +
                ", \"number of rooms\":" + numberOfRooms +
                ", \"type of room\":" + typeOfRoom.toString() + '}';
    }

 /*   @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Reservation> reservations;*/

}
