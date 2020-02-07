package com.netcracker.TravelAgency.converter;

import com.netcracker.TravelAgency.dto.AuthorizedUserDto;
import com.netcracker.TravelAgency.entity.AuthorizedUser;
import org.modelmapper.ModelMapper;

public class AuthorizedUserConverter {
    public static AuthorizedUserDto convertEntityToDto(AuthorizedUser entity) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(entity, AuthorizedUserDto.class);
    }

    public static AuthorizedUser convertDtoToEntity(AuthorizedUserDto dto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, AuthorizedUser.class);
    }
}
