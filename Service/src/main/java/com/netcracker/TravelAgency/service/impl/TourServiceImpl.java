package com.netcracker.TravelAgency.service.impl;

import com.netcracker.TravelAgency.converter.TourConverter;
import com.netcracker.TravelAgency.dto.TourDto;
import com.netcracker.TravelAgency.entity.*;
import com.netcracker.TravelAgency.exeption.BadRequestException;
import com.netcracker.TravelAgency.exeption.NotFoundException;
import com.netcracker.TravelAgency.repository.*;
import com.netcracker.TravelAgency.service.interfaces.CrudService;
import com.netcracker.TravelAgency.service.interfaces.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class TourServiceImpl implements TourService, CrudService<TourDto> {

    private TourRepository tourRepository;
    private CountryRepository countryRepository;
    private HotelRepository hotelRepository;
    private ArrivalDateRepository arrivalDateRepository;
    private RoomRepository roomRepository;
    private OperatorRepository operatorRepository;

    @Autowired
    public TourServiceImpl(TourRepository tourRepository, CountryRepository countryRepository,
                           HotelRepository hotelRepository, ArrivalDateRepository arrivalDateRepository,
                           RoomRepository roomRepository, OperatorRepository operatorRepository) {

        this.tourRepository = tourRepository;
        this.countryRepository = countryRepository;
        this.hotelRepository = hotelRepository;
        this.arrivalDateRepository = arrivalDateRepository;
        this.roomRepository = roomRepository;
        this.operatorRepository = operatorRepository;
    }

    @Override
    public int create(TourDto tourDto) {
        for (int id : tourDto.getArrivalDatesId()) {
            if (!arrivalDateRepository.findById(id).isPresent())
                throw new NotFoundException("Arrival date doesn't exist");
        }
        if (!countryRepository.findById(tourDto.getCountryId()).isPresent() || !operatorRepository.findById(tourDto.getOperatorId()).isPresent()
                || !hotelRepository.findById(tourDto.getHotelId()).isPresent() || !roomRepository.findById(tourDto.getRoomId()).isPresent())
            throw new NotFoundException("Country, or/and operator, or/and hotel, or/and room  doesn't exist");
        try {
            Tour tour = TourConverter.convertDtoToEntity(tourDto);
            List<ArrivalDate> arrivalDates = tourDto.getArrivalDatesId().stream()
                    .map(a -> arrivalDateRepository.findById(a).get())
                    .collect(Collectors.toList());
            tour.setArrivalDates(arrivalDates);
            tour.setCountry(countryRepository.findById(tourDto.getCountryId()).get());
            tour.setHotel(hotelRepository.findById(tourDto.getHotelId()).get());
            tour.setRoom(roomRepository.findById(tourDto.getRoomId()).get());
            tour.setOperator(operatorRepository.findById(tourDto.getOperatorId()).get());
            tourRepository.save(tour);
            return tour.getId();
        } catch (NoSuchElementException e) {
            throw new BadRequestException("Not valid data");
        }
    }

    @Override
    public TourDto findById(int id) {
        TourDto tourDto;
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (tourRepository.findById(id).isPresent()) {
            tourDto = TourConverter.convertEntityToDto(tourRepository.findById(id).get());
        } else {
            throw new NotFoundException("Tour not found");
        }
        return tourDto;
    }

    @Override
    public List<TourDto> findAll() {
        return tourRepository.findAll().stream().map((TourConverter::convertEntityToDto)).collect(Collectors.toList());
    }

    @Override
    public int update(TourDto tourDto) {
        for (int id : tourDto.getArrivalDatesId()) {
            if (!arrivalDateRepository.findById(id).isPresent())
                throw new NotFoundException("Arrival date doesn't exist");
        }
        if (!countryRepository.findById(tourDto.getCountryId()).isPresent() || !operatorRepository.findById(tourDto.getOperatorId()).isPresent()
                || !hotelRepository.findById(tourDto.getHotelId()).isPresent() || !roomRepository.findById(tourDto.getRoomId()).isPresent())
            throw new NotFoundException("Country, or/and operator, or/and hotel, or/and room  doesn't exist");
        Tour tour = TourConverter.convertDtoToEntity(tourDto);
        List<ArrivalDate> arrivalDates = tourDto.getArrivalDatesId().stream()
                .map(a -> arrivalDateRepository.findById(a).get())
                .collect(Collectors.toList());
        tour.setArrivalDates(arrivalDates);
        tour.setCountry(countryRepository.findById(tourDto.getCountryId()).get());
        tour.setHotel(hotelRepository.findById(tourDto.getHotelId()).get());
        tour.setRoom(roomRepository.findById(tourDto.getRoomId()).get());
        tour.setOperator(operatorRepository.findById(tourDto.getOperatorId()).get());
        if (tourDto.getId() <= 0)
            throw new BadRequestException("Not valid data");
        if (tourRepository.findById(tourDto.getId()).isPresent()) {
            tourRepository.delete(tourRepository.findById(tourDto.getId()).get());
            tourRepository.save(tour);
            return tour.getId();
        } else {
            throw new NotFoundException("Tour doesn't exist");
        }
    }

    @Override
    public void deleteById(int id) {
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (tourRepository.findById(id).isPresent()) {
            tourRepository.deleteById(id);
        } else {
            throw new NotFoundException("Tour doesn't exist");
        }
    }

    @Override
    public void delete(TourDto tourDto) {
        if (tourDto.getId() <= 0) throw new BadRequestException("Not valid id");
        if (tourRepository.findById(tourDto.getId()).isPresent()) {
            tourRepository.deleteById(tourDto.getId());
        } else {
            throw new NotFoundException("Tour doesn't exist");
        }
    }

    @Override
    public List<TourDto> findAllByCountryId(Integer countryId) {
        if (!countryRepository.findById(countryId).isPresent())
            throw new NotFoundException("Country doesn't exist");

        return tourRepository.findAllByCountry(countryRepository.getOne(countryId)).stream().
                map(TourConverter::convertEntityToDto).collect(Collectors.toList());
    }

    ////////////////////////////////////////
    @Override
    public List<TourDto> findByCriteria(GregorianCalendar arrivalDateFrom, GregorianCalendar arrivalDateTo,
                                        Integer lengthOfStayFrom, Integer lengthOfStayTo, final Hotel.HotelCategory hotelCategory,
                                        final Hotel.TypeOfFood typeOfFood, final String countryName, final String hotelName,
                                        final Integer numberOfTourists, final Room.TypeOfRoom typeOfRoom, Integer costFrom, Integer costTo) {
        if (lengthOfStayFrom == null) lengthOfStayFrom = 0;
        if (lengthOfStayTo == null) lengthOfStayTo = Integer.MAX_VALUE;
        if (costFrom == null) costFrom = 0;
        if (costTo == null) costTo = Integer.MAX_VALUE;

        List<Tour> tours = new ArrayList<>();
        List<ArrivalDate> arrivalDates = new ArrayList<>();

        if (countryName != null) {
            List<ArrivalDate> arrDates = arrivalDateRepository.findByCountryAndNumberOfFreePlacesAfter(
                    countryRepository.findByName(countryName), numberOfTourists);
            for (ArrivalDate arrDate : arrDates) {
                boolean after = arrDate.getArrivalDate().after(arrivalDateFrom);
                boolean before = arrDate.getArrivalDate().before(arrivalDateTo);
                if (after && before)
                    arrivalDates.add(arrDate);
            }
        } else arrivalDates = arrivalDateRepository.findByArrivalDateBetweenAndNumberOfFreePlacesAfter(
                arrivalDateFrom, arrivalDateTo, numberOfTourists);
        if (arrivalDates.size() == 0) return null;


        Integer countryId = countryRepository.findByName(countryName).getId();

        Integer hotelId = hotelRepository.findByNameAndCountry(hotelName, countryRepository.findByName(countryName)).getId();
        List<Room> roomList = roomRepository.findByHotelAndNumberOfRoomsAfterAndNumberOfSleepingPlacesAfterAndTypeOfRoom(
                hotelRepository.findByNameAndCountry(hotelName, countryRepository.findByName(countryName)), 1, numberOfTourists - 1, typeOfRoom);

        for (Room room : roomList) {
            Integer hotelCat = hotelCategory.ordinal();
            Integer foodType = typeOfFood.ordinal();
            List<Tour> t = tourRepository.findByCountryAndHotelAndHotelCategoryAndTypeOfFoodAndLengthOfStayBetweenAndRoom_IdAndCostBetween(
                    hotelCat, lengthOfStayFrom, lengthOfStayTo, foodType, countryId, hotelId, room.getId(), costFrom, costTo);
            tours.addAll(t);
        }

        return tours.stream().map(TourConverter::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public List<TourDto> findAllWithRoomInTour(Integer tourId) {
        if (!tourRepository.findById(tourId).isPresent())
            throw new NotFoundException("Tour doesn't exist");

        Integer roomId = tourRepository.getOne(tourId).getRoom().getId();
        Integer countryId = tourRepository.getOne(tourId)
                .getCountry().getId();

        return tourRepository.findByCountry_IdAndRoom_Id(countryId, roomId).stream().map((TourConverter::convertEntityToDto)).collect(Collectors.toList());
    }

    @Override
    public void deleteArrivalDateInTour(Integer tourId, Integer arrivalDateId) {
        if (!tourRepository.findById(tourId).isPresent() || !arrivalDateRepository.findById(arrivalDateId).isPresent())
            throw new NotFoundException("Tour or/and arrival date doesn't exist");

        List<Tour> tours = tourRepository.findAllByCountry(tourRepository.getOne(tourId).getCountry());
        for (Tour tour : tours) {
            List<ArrivalDate> arrivalDates = tour.getArrivalDates();
            arrivalDates.remove(arrivalDateRepository.getOne(arrivalDateId));
            tour.setArrivalDates(arrivalDates);
            update(TourConverter.convertEntityToDto(tour));
        }
    }

}
