package com.netcracker.TravelAgency.dto;


import com.netcracker.TravelAgency.entity.AuthorizedUser;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class OperatorDto extends AuthorizedUserDto {

    private List<Integer> hotelsId;
    private List<Integer> toursId;


    public static Builder builder() {
        return new OperatorDto().new Builder();
    }

    public class Builder {
        private Builder() {
        }

        public Builder withId(final Integer id) {
            OperatorDto.super.setId(id);
            return this;
        }

        public Builder withPassword(final String password) {
            OperatorDto.super.setPassword(password);
            return this;
        }


        public Builder withName(final String name) {
            OperatorDto.super.setName(name);
            return this;
        }

        public Builder withLogin(final String login) {
            OperatorDto.super.setLogin(login);
            return this;
        }

        public Builder withToursId(final List<Integer> tours) {
            OperatorDto.this.toursId = tours;
            return this;
        }

        public Builder withHotelsId(final List<Integer> hotels) {
            OperatorDto.this.hotelsId = hotels;
            return this;
        }

        public OperatorDto build() {
            return OperatorDto.this;
        }
    }

    public OperatorDto() {
    }

    public OperatorDto(int id, String login, String password, String name) {
        super(id, login, password, name, AuthorizedUser.Role.OPERATOR);
        this.hotelsId = new ArrayList<>();
        this.toursId = new ArrayList<>();
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
        OperatorDto that = (OperatorDto) o;
        return Objects.equals(hotelsId, that.hotelsId) &&
                Objects.equals(toursId, that.toursId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), hotelsId, toursId);
    }
}
