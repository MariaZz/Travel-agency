package com.netcracker.TravelAgency.converter;

import com.netcracker.TravelAgency.dto.ArrivalDateDto;
import com.netcracker.TravelAgency.entity.ArrivalDate;
import org.modelmapper.ModelMapper;

public class ArrivalDateConverter {

    public static ArrivalDateDto convertEntityToDto(ArrivalDate entity) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(entity, ArrivalDateDto.class);
    }

    public static ArrivalDate convertDtoToEntity(ArrivalDateDto dto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, ArrivalDate.class);
    }
}
