package com.netcracker.TravelAgency.service.interfaces;

import com.netcracker.TravelAgency.dto.ClientDto;

import java.util.List;

public interface ClientService {

    void reservationTrip(final Integer clientId, final Integer tripId, final String contacts);

    ClientDto findByTripId(final Integer tripId);

    boolean loginAlreadyExist(final String login);

    void payClientTripById(final int id);
}
