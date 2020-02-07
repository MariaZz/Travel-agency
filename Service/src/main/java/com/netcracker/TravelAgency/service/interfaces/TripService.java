package com.netcracker.TravelAgency.service.interfaces;

import com.netcracker.TravelAgency.dto.TouristDto;
import com.netcracker.TravelAgency.dto.TripDto;

import java.util.GregorianCalendar;
import java.util.List;

public interface TripService {

    TripDto reserveTrip(final Integer tourId, final GregorianCalendar arrivalDate, final List<TouristDto> tourists);
}
