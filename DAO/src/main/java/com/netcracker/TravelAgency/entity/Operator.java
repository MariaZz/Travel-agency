package com.netcracker.TravelAgency.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "\"Operator\"")
@PrimaryKeyJoinColumn(name = "id")
public class Operator extends AuthorizedUser {

    @OneToMany(mappedBy = "operator", cascade = CascadeType.ALL)
    private List<Hotel> hotels;

    @OneToMany(mappedBy = "operator", cascade = CascadeType.ALL)
    private List<Tour> tours;

    public Operator(){}

    public Operator(int id, String login, String password, String name) {
        super(id, login, password, name,Role.OPERATOR);
        this.hotels = new ArrayList<>();
        this.tours = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                ", \"role\":\"" + getRole() + '\"' +
                ", \"login\":\"" + getLogin() + '\"' +
                ", \"password\":\"" + getPassword() + '\"' +
                ", \"name\":\"" + getName() + '\"' + '}';
    }
}
