package com.netcracker.TravelAgency.converter;

import com.netcracker.TravelAgency.dto.TripDto;
import com.netcracker.TravelAgency.entity.Trip;
import org.modelmapper.ModelMapper;

public class TripConverter {

    public static TripDto convertEntityToDto(Trip entity) {
        ModelMapper mapper = new ModelMapper();
        return  mapper.map(entity, TripDto.class);
    }

    public static Trip convertDtoToEntity(TripDto dto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, Trip.class);
    }

}
