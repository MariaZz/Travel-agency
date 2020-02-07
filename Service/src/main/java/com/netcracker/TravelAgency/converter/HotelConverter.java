package com.netcracker.TravelAgency.converter;

import com.netcracker.TravelAgency.dto.HotelDto;
import com.netcracker.TravelAgency.entity.Hotel;
import org.modelmapper.ModelMapper;


public class HotelConverter {

    public static HotelDto convertEntityToDto(Hotel entity) {
        ModelMapper mapper = new ModelMapper();
        return  mapper.map(entity, HotelDto.class);
    }

    public static Hotel convertDtoToEntity(HotelDto dto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, Hotel.class);
    }

}
