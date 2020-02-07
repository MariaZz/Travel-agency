package com.netcracker.TravelAgency.service.interfaces;

import com.netcracker.TravelAgency.dto.AuthorizedUserDto;

import java.util.List;
import java.util.Optional;

public interface AuthorizedUserService {

    Optional<AuthorizedUserDto> findByLogin(final String login);

}
