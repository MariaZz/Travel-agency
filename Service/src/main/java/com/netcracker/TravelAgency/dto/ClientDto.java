package com.netcracker.TravelAgency.dto;


import com.netcracker.TravelAgency.entity.AuthorizedUser;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class ClientDto extends AuthorizedUserDto {

    private String contacts;
    private Integer tripId;
    private Boolean payment;
    private Integer travelAgentId;

    public static Builder builder() {
        return new ClientDto().new Builder();
    }


    public class Builder {
        private Builder() {
        }

        public Builder withId(final Integer id) {
            ClientDto.super.setId(id);
            return this;
        }

        public  Builder withPassword(final String password){
            ClientDto.super.setPassword(password);
            return this;
        }


        public Builder withName(final String name) {
            ClientDto.super.setName(name);
            return this;
        }

        public  Builder withLogin(final String login){
            ClientDto.super.setLogin(login);
            return this;
        }


        public Builder withContacts(final String contacts) {
            ClientDto.this.contacts = contacts;
            return this;
        }

        public Builder withTripId(final Integer trip) {
            ClientDto.this.tripId = trip;
            return this;
        }

        public Builder withPayment(final Boolean payment) {
            ClientDto.this.payment = payment;
            return this;
        }

        public Builder withTravelAgentId(final Integer travelAgent) {
            ClientDto.this.travelAgentId = travelAgent;
            return this;
        }

        public ClientDto build() {
            return ClientDto.this;
        }
    }
    public ClientDto() {
    }

    public ClientDto(int id, String login, String password, String name,  String contacts) {
        super(id, login, password, name, AuthorizedUser.Role.CLIENT);
        this.contacts = contacts;
        this.payment = false;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + getId() +
                ", \"role\":\"" + getRole() + '\"' +
                ", \"login\":\"" + getLogin() + '\"' +
                ", \"password\":\"" + getPassword() + '\"' +
                ", \"name\":\"" + getName() + '\"' +
                ", \"contacts\":\"" + getContacts() + '\"' +
                ", \"payment\":" + getPayment() +
                ", \"trip\":\"" + getTripId() + '\"' +
                ", \"travel agent\":" + getTravelAgentId() + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClientDto clientDto = (ClientDto) o;
        return Objects.equals(contacts, clientDto.contacts) &&
                Objects.equals(tripId, clientDto.tripId) &&
                Objects.equals(payment, clientDto.payment) &&
                Objects.equals(travelAgentId, clientDto.travelAgentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contacts, tripId, payment, travelAgentId);
    }
}

