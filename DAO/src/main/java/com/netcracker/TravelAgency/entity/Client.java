package com.netcracker.TravelAgency.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "\"Client\"")
@PrimaryKeyJoinColumn(name = "id")
public class Client extends AuthorizedUser {
    @Size(min = 2, message = "Contacts must be 2 or longer than 2 characters")
    @Column(name = "contacts")
    private String contacts;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @Column(name = "payment")
    private Boolean payment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travelAgent_id", nullable = false)
    private TravelAgent travelAgent;

    public Client(){};
    public Client(int id, String login, String password, String name,  String contacts) {
        super(id, login, password, name, Role.CLIENT);
        this.contacts = contacts;
        this.payment = false;
    }

 /*   @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                ", \"role\":\"" + getRole() + '\"' +
                ", \"login\":\"" + getLogin() + '\"' +
                ", \"password\":\"" + getPassword() + '\"' +
                ", \"name\":\"" + getName() + '\"' +
                ", \"contacts\":\"" + getContacts() + '\"' +
                ", \"payment\":" + getPayment() +
                ", \"trip\":\"" + getTrip().getId() + '\"' +
                ", \"travel agent\":" + getTravelAgent().getId() + '}';
    }*/

}
