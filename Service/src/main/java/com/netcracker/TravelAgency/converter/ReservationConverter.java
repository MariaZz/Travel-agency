package com.netcracker.TravelAgency.converter;

import com.netcracker.TravelAgency.dto.ReservationDto;
import com.netcracker.TravelAgency.entity.Reservation;
import org.modelmapper.ModelMapper;

public class ReservationConverter  {

    public static ReservationDto convertEntityToDto(Reservation entity) {
        ModelMapper mapper = new ModelMapper();
        return  mapper.map(entity, ReservationDto.class);
    }

    public static Reservation convertDtoToEntity(ReservationDto dto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(dto, Reservation.class);
    }

}
