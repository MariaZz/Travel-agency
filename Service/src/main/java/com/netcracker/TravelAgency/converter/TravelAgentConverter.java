package com.netcracker.TravelAgency.converter;

import com.netcracker.TravelAgency.dto.TravelAgentDto;
import com.netcracker.TravelAgency.entity.TravelAgent;
import org.modelmapper.ModelMapper;

public class TravelAgentConverter {

    public static TravelAgentDto convertEntityToDto(TravelAgent entity) {
        ModelMapper mapper = new ModelMapper();
        return  mapper.map(entity, TravelAgentDto.class);
    }

    public static TravelAgent convertDtoToEntity(TravelAgentDto dto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, TravelAgent.class);
    }
}
