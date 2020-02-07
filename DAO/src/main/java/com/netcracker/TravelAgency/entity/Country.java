package com.netcracker.TravelAgency.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "\"Country\"")
public class Country extends BaseEntity {

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travelAgent_id", nullable = false)
    private TravelAgent travelAgent;

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                "\"name\":" + name +
                ", \"travelAgent\":" + travelAgent.getId() + '}';
    }
}
