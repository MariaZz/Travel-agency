package com.netcracker.TravelAgency.converter;

import com.netcracker.TravelAgency.dto.RoomDto;
import com.netcracker.TravelAgency.entity.Room;
import org.modelmapper.ModelMapper;

public class RoomConverter {

    public static RoomDto convertEntityToDto(Room entity) {
        ModelMapper mapper = new ModelMapper();
        return  mapper.map(entity, RoomDto.class);
    }

    public static Room convertDtoToEntity(RoomDto dto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, Room.class);
    }

}
