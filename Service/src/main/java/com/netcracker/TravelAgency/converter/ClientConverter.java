package com.netcracker.TravelAgency.converter;

import com.netcracker.TravelAgency.dto.ClientDto;

import com.netcracker.TravelAgency.entity.Client;
import org.modelmapper.ModelMapper;

public class ClientConverter {

    public static ClientDto convertEntityToDto(Client entity) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(entity, ClientDto.class);
    }


    public static Client convertDtoToEntity(ClientDto dto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, Client.class);
    }
}
