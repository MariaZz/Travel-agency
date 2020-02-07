package com.netcracker.TravelAgency.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.GregorianCalendar;

@Getter
@Setter
@Entity
@Table(name = "\"Reservation\"")
public class Reservation extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @NotNull(message = "Room cannot be null")
    private Room room;

    @NotNull(message = "Reservation date cannot be null")
    @Column(name = "reservationDate")
    private GregorianCalendar reservationDate;

    @Column(name = "numberOfRooms")
    private Integer numberOfRooms;

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                " \"room\":" + room.getId() +
                ", \"reservation date\":" + reservationDate +
                ", \"number of rooms\":" + numberOfRooms + '}';
    }
}
