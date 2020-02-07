package com.netcracker.TravelAgency.service.impl;

import com.netcracker.TravelAgency.converter.ArrivalDateConverter;
import com.netcracker.TravelAgency.dto.ArrivalDateDto;
import com.netcracker.TravelAgency.entity.ArrivalDate;
import com.netcracker.TravelAgency.exeption.BadRequestException;
import com.netcracker.TravelAgency.exeption.NotFoundException;
import com.netcracker.TravelAgency.repository.ArrivalDateRepository;
import com.netcracker.TravelAgency.repository.CountryRepository;
import com.netcracker.TravelAgency.repository.TourRepository;
import com.netcracker.TravelAgency.service.interfaces.ArrivalDateService;
import com.netcracker.TravelAgency.service.interfaces.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ArrivalDateServiceImpl implements ArrivalDateService, CrudService<ArrivalDateDto> {

    private ArrivalDateRepository arrivalDateRepository;
    private TourRepository tourRepository;
    private CountryRepository countryRepository;


    @Autowired
    public ArrivalDateServiceImpl(ArrivalDateRepository arrivalDateRepository, TourRepository tourRepository, CountryRepository countryRepository) {

        this.arrivalDateRepository = arrivalDateRepository;
        this.tourRepository = tourRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public Integer bookArrivalDate(final Integer arrivalDateId, final Integer numberOfPlaces) {
        if (arrivalDateId <= 0)
            throw new BadRequestException("Not valid data");
        if (arrivalDateRepository.findById(arrivalDateId).isPresent()) {
            ArrivalDate arrivalDate = arrivalDateRepository.getOne(arrivalDateId);
            Integer places = arrivalDate.getNumberOfFreePlaces() - numberOfPlaces;
            if (places < 0)
                throw new BadRequestException("Not valid data");
            arrivalDate.setNumberOfFreePlaces(places);
            update(ArrivalDateConverter.convertEntityToDto(arrivalDate));
            return places;
        } else {
            throw new NotFoundException("Arrival date doesn't exist");
        }
    }


    @Override
    public List<ArrivalDateDto> findByTourIdCountry(final Integer tourId) {
        if (tourId <= 0)
            throw new BadRequestException("Not valid data");
        if (tourRepository.findById(tourId).isPresent()) {
            List<ArrivalDate> arrivalDate = arrivalDateRepository.findByCountry(tourRepository.getOne(tourId).getCountry());
            return arrivalDate.stream().map((ArrivalDateConverter::convertEntityToDto)).collect(Collectors.toList());
        } else {
            throw new NotFoundException("Tour doesn't exist");
        }
    }

    @Override
    public Integer cancelArrivalDate(final Integer arrivalDateId, final Integer numberOfPlaces) {
        if (arrivalDateId <= 0)
            throw new BadRequestException("Not valid data");
        if (arrivalDateRepository.findById(arrivalDateId).isPresent()) {
            ArrivalDate arrivalDate = arrivalDateRepository.getOne(arrivalDateId);
            Integer places = arrivalDate.getNumberOfFreePlaces() + numberOfPlaces;
            arrivalDate.setNumberOfFreePlaces(places);
            update(ArrivalDateConverter.convertEntityToDto(arrivalDate));
            return places;
        } else {
            throw new NotFoundException("Arrival date doesn't exist");
        }
    }

    @Override
    public ArrivalDateDto searchArrivalDate(final GregorianCalendar date, final Integer tourId) {
        if (tourId <= 0 || !tourRepository.findById(tourId).isPresent() || date.before(GregorianCalendar.getInstance()))
            throw new BadRequestException("Not valid data");
        ArrivalDate arrivalDate = arrivalDateRepository.findAllByCountryAndAndArrivalDate(
                tourRepository.getOne(tourId).getCountry(), date);
        if (arrivalDate != null)
            return ArrivalDateConverter.convertEntityToDto(arrivalDate);
        else {
            throw new NotFoundException("Arrival date doesn't exist");
        }
    }

    @Override
    public int update(final ArrivalDateDto arrivalDateDto) {
        if (arrivalDateDto.getNumberOfFreePlaces() < 0 || arrivalDateDto.getArrivalDate().before(GregorianCalendar.getInstance())
                || !countryRepository.findById(arrivalDateDto.getCountryId()).isPresent())
            throw new BadRequestException("Country doesn't exist");
        ArrivalDate arrivalDate = ArrivalDateConverter.convertDtoToEntity(arrivalDateDto);
        arrivalDate.setCountry(countryRepository.findById(arrivalDateDto.getCountryId()).get());
        if (arrivalDateDto.getId() <= 0)
            throw new BadRequestException("Not valid data");
        if (arrivalDateRepository.findById(arrivalDateDto.getId()).isPresent()) {
            arrivalDateRepository.delete(arrivalDateRepository.findById(arrivalDateDto.getId()).get());
            arrivalDateRepository.save(arrivalDate);
            return arrivalDate.getId();
        } else {
            throw new NotFoundException("Arrival date doesn't exist");
        }
    }

    @Override
    public int create(final ArrivalDateDto arrivalDateDto) {
        if (arrivalDateDto.getNumberOfFreePlaces() < 0 || arrivalDateDto.getArrivalDate().before(GregorianCalendar.getInstance())
                || !countryRepository.findById(arrivalDateDto.getCountryId()).isPresent())
            throw new BadRequestException("Not valid data or/and country doesn't exist");
        try {
            ArrivalDate arrivalDate = ArrivalDateConverter.convertDtoToEntity(arrivalDateDto);
            arrivalDate.setCountry(countryRepository.findById(arrivalDateDto.getCountryId()).get());
            arrivalDateRepository.save(arrivalDate);
            return arrivalDate.getId();
        } catch (NoSuchElementException e) {
            throw new BadRequestException("Not valid data");
        }
    }

    @Override
    public List<ArrivalDateDto> findAll() {
        return arrivalDateRepository.findAll().stream().map(ArrivalDateConverter::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ArrivalDateDto findById(final int id) {
        ArrivalDateDto arrivalDateDto;
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (arrivalDateRepository.findById(id).isPresent()) {
            arrivalDateDto = ArrivalDateConverter.convertEntityToDto(arrivalDateRepository.findById(id).get());
        } else {
            throw new NotFoundException("Arrival date not found");
        }
        return arrivalDateDto;
    }


    @Override
    public void delete(final ArrivalDateDto arrivalDateDto) {
        if (arrivalDateDto.getId() <= 0) throw new BadRequestException("Not valid id");
        if (arrivalDateRepository.findById(arrivalDateDto.getId()).isPresent()) {
            arrivalDateRepository.deleteById(arrivalDateDto.getId());
        } else {
            throw new NotFoundException("Arrival date doesn't exist");
        }
    }

    @Override
    public void deleteById(final int id) {
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (arrivalDateRepository.findById(id).isPresent()) {
            arrivalDateRepository.deleteById(id);
        } else {
            throw new NotFoundException("Arrival date doesn't exist");
        }
    }
}
