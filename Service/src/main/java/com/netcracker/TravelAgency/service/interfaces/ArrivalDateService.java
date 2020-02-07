package com.netcracker.TravelAgency.service.interfaces;

import com.netcracker.TravelAgency.dto.ArrivalDateDto;

import java.util.GregorianCalendar;
import java.util.List;

public interface ArrivalDateService {

    ArrivalDateDto searchArrivalDate(final GregorianCalendar date, final Integer tourId);

    Integer cancelArrivalDate(final Integer arrivalDateId, final Integer numberOfPlaces);

    List<ArrivalDateDto> findByTourIdCountry(final Integer tourId);

    Integer bookArrivalDate(final Integer arrivalDateId, final Integer numberOfPlaces);
}

