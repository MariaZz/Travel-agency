package com.netcracker.TravelAgency.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "\"Tourist\"")
public class Tourist extends BaseEntity {

    @NotNull(message = "Passport data cannot be null")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(name = "passportdata")
    private String passportData;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                " \"password data \":" + passportData +
                ", \"trip\":" + trip.getId() + '}';
    }
}
