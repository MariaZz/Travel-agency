package com.netcracker.TravelAgency.service.interfaces;

import com.netcracker.TravelAgency.dto.TouristDto;
import com.netcracker.TravelAgency.dto.TripDto;

import java.util.List;

public interface TouristService {

    List<TouristDto> getTouristsList(final String[] tourists, final TripDto tripDto);
}
