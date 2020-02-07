package com.netcracker.TravelAgency.dto;

import com.netcracker.TravelAgency.entity.AuthorizedUser;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class AuthorizedUserDto extends BaseEntityDto {

    private String name;
    private String login;
    private String password;
    private AuthorizedUser.Role role;

    public AuthorizedUserDto() { }

    public AuthorizedUserDto(int id, String login, String password, String name, AuthorizedUser.Role role) {
        super(id);
        this.login = login;
        this.password = password;
        this.name = name;
        this.role = role;
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
        AuthorizedUserDto that = (AuthorizedUserDto) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(login, that.login) &&
                Objects.equals(password, that.password) &&
                role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, login, password, role);
    }
}
