package com.netcracker.TravelAgency.entity;



import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "\"AuthorisedUser\"")
@Inheritance(strategy = InheritanceType.JOINED)
public class AuthorizedUser extends BaseEntity {
    @NotNull(message = "Name cannot be null")
    @Size(min = 1, max = 20, message = "Name must be between 1 and 20 characters")
    @Column(name = "name")
    private String name;

    @NotNull
    @Size(min = 1, max = 20, message = "Login must be between 1 and 20 characters")
    @Column(name="login")
    private String login;

    @NotNull(message = "Password cannot be null")
    @Size(min = 1, max = 20, message = "Password must be between 1 and 20 characters")
    @Column(name = "password")
    private String password;

    @Enumerated
    @NotNull(message = "Role cannot be null")
    @Column(name = "role")
  /*  private String login;
    private String password;
    private String name;*/
    private Role role;

    public enum Role {
        OPERATOR,
        CLIENT,
        TRAVEL_AGENT
    }

    public AuthorizedUser() { }

    public AuthorizedUser(int id, String login, String password, String name, AuthorizedUser.Role role) {
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
}
