package com.netcracker.TravelAgency.converter;

import com.netcracker.TravelAgency.dto.OperatorDto;
import com.netcracker.TravelAgency.entity.Operator;
import org.modelmapper.ModelMapper;


public class OperatorConverter  {

    public static OperatorDto convertEntityToDto(Operator entity) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(entity, OperatorDto.class);
    }

    public static Operator convertDtoToEntity(OperatorDto dto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, Operator.class);
    }

}
