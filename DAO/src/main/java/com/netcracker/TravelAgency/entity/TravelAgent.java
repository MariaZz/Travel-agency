package com.netcracker.TravelAgency.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "\"TravelAgent\"")
@PrimaryKeyJoinColumn(name = "id")
public class TravelAgent extends AuthorizedUser {

    @OneToMany(mappedBy = "travelAgent")
    private List<Country> countries;

    @OneToMany(mappedBy = "travelAgent", cascade = CascadeType.ALL)
    private List<Client> clients;

    public TravelAgent() {
    }

    public TravelAgent(int id, String login, String password, String name) {
        super(id, login, password, name, Role.TRAVEL_AGENT);
        this.countries = new ArrayList<>();
        this.clients = new ArrayList<>();
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
