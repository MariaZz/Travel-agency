package com.netcracker.TravelAgency.dto;

import com.netcracker.TravelAgency.entity.AuthorizedUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(callSuper = true)
@Data
public class TravelAgentDto extends AuthorizedUserDto {

    private List<Integer> countriesId;
    private List<Integer> clientsId;

    public static Builder builder() {
        return new TravelAgentDto().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder withId(final Integer id) {
            TravelAgentDto.super.setId(id);
            return this;
        }

        public Builder withPassword(final String password) {
            TravelAgentDto.super.setPassword(password);
            return this;
        }


        public Builder withName(final String name) {
            TravelAgentDto.super.setName(name);
            return this;
        }

        public Builder withLogin(final String login) {
            TravelAgentDto.super.setLogin(login);
            return this;
        }


        public Builder withClientsId(final List<Integer> clients) {
            TravelAgentDto.this.clientsId = clients;
            return this;
        }

        public Builder withCountriesId(final List<Integer> countries) {
            TravelAgentDto.this.countriesId = countries;
            return this;
        }

        public TravelAgentDto build() {
            return TravelAgentDto.this;
        }
    }

    public TravelAgentDto() {
    }

    public TravelAgentDto(int id, String login, String password, String name) {
        super(id, login, password, name, AuthorizedUser.Role.TRAVEL_AGENT);
        this.countriesId = new ArrayList<>();
        this.clientsId = new ArrayList<>();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TravelAgentDto that = (TravelAgentDto) o;
        return Objects.equals(countriesId, that.countriesId) &&
                Objects.equals(clientsId, that.clientsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), countriesId, clientsId);
    }
}
