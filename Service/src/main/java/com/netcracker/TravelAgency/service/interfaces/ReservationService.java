package com.netcracker.TravelAgency.service.interfaces;

import com.netcracker.TravelAgency.dto.RoomDto;

import java.util.GregorianCalendar;
import java.util.List;

public interface ReservationService {

    List<GregorianCalendar> cancelReservation(final Integer tourId, final List<GregorianCalendar> date);

    List<GregorianCalendar> searchReservation(final Integer tourId, final GregorianCalendar date);

    Integer checkReservation(final Integer tourId, final RoomDto roomDto, final GregorianCalendar date);
}
