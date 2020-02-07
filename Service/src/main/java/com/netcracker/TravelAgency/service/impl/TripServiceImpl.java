package com.netcracker.TravelAgency.service.impl;

import com.netcracker.TravelAgency.converter.TouristConverter;
import com.netcracker.TravelAgency.converter.TripConverter;
import com.netcracker.TravelAgency.dto.TouristDto;
import com.netcracker.TravelAgency.dto.TripDto;
import com.netcracker.TravelAgency.entity.*;
import com.netcracker.TravelAgency.exeption.BadRequestException;
import com.netcracker.TravelAgency.exeption.NotFoundException;
import com.netcracker.TravelAgency.repository.*;
import com.netcracker.TravelAgency.service.interfaces.CrudService;
import com.netcracker.TravelAgency.service.interfaces.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TripServiceImpl implements TripService, CrudService<TripDto> {
    private TripRepository tripRepository;
    private TourRepository tourRepository;
    private TouristRepository touristRepository;

    @Autowired
    public TripServiceImpl(TripRepository tripRepository, TourRepository tourRepository, TouristRepository touristRepository) {

        this.tripRepository = tripRepository;
        this.tourRepository = tourRepository;
        this.touristRepository = touristRepository;
    }


    @Override
    public TripDto reserveTrip(Integer tourId, GregorianCalendar arrivalDate, List<TouristDto> tourists) {
        Trip trip = new Trip();
        Tour tour = tourRepository.findAllById(tourId);
        trip.setTour(tour);
        trip.setArrivalDate(arrivalDate);
        trip.setTourists(tourists.stream().map(TouristConverter::convertDtoToEntity).collect(Collectors.toList()));
        int id = create(TripConverter.convertEntityToDto(trip));
        TripDto tripResult = new TripDto();
        tripResult.setId(id);
        tripResult = TripConverter.convertEntityToDto(tripRepository.getOne(id));
        return tripResult;
    }

    @Override
    public List<TripDto> findAll() {
        return tripRepository.findAll().stream().map((TripConverter::convertEntityToDto)).collect(Collectors.toList());
    }

    @Override
    public TripDto findById(int id) {
        TripDto tripDto;
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (tripRepository.findById(id).isPresent()) {
            tripDto = TripConverter.convertEntityToDto(tripRepository.findById(id).get());
        } else {
            throw new NotFoundException("Trip not found");
        }
        return tripDto;
    }

    @Override
    public void delete(TripDto tripDto) {
        if (tripDto.getId() <= 0) throw new BadRequestException("Not valid id");
        if (tripRepository.findById(tripDto.getId()).isPresent()) {
            tripRepository.deleteById(tripDto.getId());
        } else {
            throw new NotFoundException("Trip doesn't exist");
        }
    }

    @Override
    public void deleteById(int id) {
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (tripRepository.findById(id).isPresent()) {
            tripRepository.deleteById(id);
        } else {
            throw new NotFoundException("Trip doesn't exist");
        }
    }

    @Override
    public int create(final TripDto tripDto) {
        if (!tourRepository.findById(tripDto.getTourId()).isPresent())
            throw new NotFoundException("Tour doesn't exist");
        for (int id : tripDto.getTouristsId()) {
            if (!touristRepository.findById(id).isPresent())
                throw new NotFoundException("Tourist doesn't exist");
        }
        try {
            Trip trip = TripConverter.convertDtoToEntity(tripDto);
            trip.setTour(tourRepository.findById(tripDto.getTourId()).get());
            List<Tourist> tourists = tripDto.getTouristsId().stream()
                    .map(a -> touristRepository.findById(a).get())
                    .collect(Collectors.toList());
            trip.setTourists(tourists);
            tripRepository.save(trip);
            return trip.getId();
        } catch (NoSuchElementException e) {
            throw new BadRequestException("Not valid data");
        }
    }

    @Override
    public int update(TripDto tripDto) {
        if (!tourRepository.findById(tripDto.getTourId()).isPresent())
            throw new NotFoundException("Tour doesn't exist");
        for (int id : tripDto.getTouristsId()) {
            if (!touristRepository.findById(id).isPresent())
                throw new NotFoundException("Tourist doesn't exist");
        }
        Trip trip = TripConverter.convertDtoToEntity(tripDto);
        trip.setTour(tourRepository.findById(tripDto.getTourId()).get());
        List<Tourist> tourists = tripDto.getTouristsId().stream()
                .map(a -> touristRepository.findById(a).get())
                .collect(Collectors.toList());
        trip.setTourists(tourists);
        if (tripDto.getId() <= 0)
            throw new BadRequestException("Not valid data");
        if (tripRepository.findById(tripDto.getId()).isPresent()) {
            tripRepository.delete(tripRepository.findById(tripDto.getId()).get());
            tripRepository.save(trip);
            return trip.getId();
        } else {
            throw new NotFoundException("Trip doesn't exist");
        }
    }
}
