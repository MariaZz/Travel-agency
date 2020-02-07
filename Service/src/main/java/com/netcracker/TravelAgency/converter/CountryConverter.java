package com.netcracker.TravelAgency.converter;

import com.netcracker.TravelAgency.dto.CountryDto;
import com.netcracker.TravelAgency.entity.Country;
import org.modelmapper.ModelMapper;

public class CountryConverter {

    public static CountryDto convertEntityToDto(Country entity) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(entity, CountryDto.class);
    }

    public static Country convertDtoToEntity(CountryDto dto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, Country.class);
    }
}
