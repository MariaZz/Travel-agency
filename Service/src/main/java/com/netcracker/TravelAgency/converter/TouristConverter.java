package com.netcracker.TravelAgency.converter;

import com.netcracker.TravelAgency.dto.TouristDto;
import com.netcracker.TravelAgency.entity.Tourist;
import org.modelmapper.ModelMapper;

public class TouristConverter{

    public static TouristDto convertEntityToDto(Tourist entity) {
        ModelMapper mapper = new ModelMapper();
        return  mapper.map(entity, TouristDto.class);
    }

    public static Tourist convertDtoToEntity(TouristDto dto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, Tourist.class);
    }

}
