package com.netcracker.TravelAgency.converter;

import com.netcracker.TravelAgency.dto.TourDto;
import com.netcracker.TravelAgency.entity.Tour;
import org.modelmapper.ModelMapper;

public class TourConverter {

    public static TourDto convertEntityToDto(Tour entity) {
        ModelMapper mapper = new ModelMapper();
        return  mapper.map(entity, TourDto.class);
    }

    public static Tour convertDtoToEntity(TourDto dto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, Tour.class);
    }

}
