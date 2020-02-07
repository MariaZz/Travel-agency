package com.netcracker.TravelAgency.service.interfaces;

import com.netcracker.TravelAgency.dto.TourDto;
import com.netcracker.TravelAgency.entity.Hotel;
import com.netcracker.TravelAgency.entity.Room;

import java.util.GregorianCalendar;
import java.util.List;

public interface TourService {

    List<TourDto> findAllByCountryId(final Integer countryId);

    List<TourDto> findAllWithRoomInTour(final Integer tourId);

    void deleteArrivalDateInTour(final Integer tourId, final Integer arrivalDateId);

    List<TourDto> findByCriteria(final GregorianCalendar arrivalDateFrom, final GregorianCalendar arrivalDateTo,
                                 final Integer lengthOfStayFrom, final Integer lengthOfStayTo, final Hotel.HotelCategory hotelCategory,
                                 final Hotel.TypeOfFood typeOfFood, final String countryName, final String hotelName,
                                 final Integer numberOfTourists, final Room.TypeOfRoom typeOfRoom, final Integer costFrom, final Integer costTo);

}
