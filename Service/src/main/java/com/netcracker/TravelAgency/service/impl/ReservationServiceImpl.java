package com.netcracker.TravelAgency.service.impl;

import com.netcracker.TravelAgency.converter.ReservationConverter;
import com.netcracker.TravelAgency.dto.ReservationDto;
import com.netcracker.TravelAgency.dto.RoomDto;
import com.netcracker.TravelAgency.entity.Reservation;
import com.netcracker.TravelAgency.exeption.BadRequestException;
import com.netcracker.TravelAgency.exeption.NotFoundException;
import com.netcracker.TravelAgency.repository.ReservationRepository;
import com.netcracker.TravelAgency.repository.RoomRepository;
import com.netcracker.TravelAgency.repository.TourRepository;
import com.netcracker.TravelAgency.service.interfaces.CrudService;
import com.netcracker.TravelAgency.service.interfaces.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImpl implements ReservationService, CrudService<ReservationDto> {

    private ReservationRepository reservationRepository;
    private TourRepository tourRepository;
    private RoomRepository roomRepository;

    @Autowired
    public ReservationServiceImpl(TourRepository tourRepository, ReservationRepository reservationRepository, RoomRepository roomRepository) {

        this.reservationRepository = reservationRepository;
        this.tourRepository = tourRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public Integer checkReservation(Integer tourId, RoomDto roomDto, GregorianCalendar date) {
        if (!tourRepository.findById(tourId).isPresent() || !roomRepository.findById(roomDto.getId()).isPresent())
            throw new NotFoundException("Tour or/and room doesn't exist");

        Integer roomId = roomDto.getId();
        Integer length = tourRepository.getOne(tourId).getLengthOfStay();
        int freeRooms = roomDto.getNumberOfRooms() - 1;
        Integer allRooms = roomDto.getNumberOfRooms();
        for (int i = 0; i < length; i++) {

            Optional<Reservation> reserv = reservationRepository.findFirstByReservationDateAndRoom(date, roomRepository.getOne(roomId));

            if (reserv.isPresent()) {
                int reservId = reserv.get().getId();
                Integer rooms = reserv.get().getNumberOfRooms();
                rooms++;
                ReservationDto reservationDto = new ReservationDto();
                reservationDto.setId(reservId);
                reservationDto.setRoomId(roomId);
                reservationDto.setNumberOfRooms(rooms);
                reservationDto.setReservationDate(date);
                update(reservationDto);
                if (freeRooms > allRooms - rooms) freeRooms = allRooms - rooms;
            } else {
                Reservation reservation = new Reservation();
                reservation.setReservationDate(date);
                reservation.setRoom(roomRepository.getOne(roomId));
                reservation.setNumberOfRooms(1);
                create(ReservationConverter.convertEntityToDto(reservation));
            }
            date.add(Calendar.DATE, 1);
        }
        return freeRooms;
    }

    @Override
    public List<GregorianCalendar> searchReservation(Integer tourId, GregorianCalendar date) {

        if (!tourRepository.findById(tourId).isPresent())
            throw new NotFoundException("Tour doesn't exist");

        Integer roomId = tourRepository.getOne(tourId).getRoom().getId();
        Integer length = tourRepository.getOne(tourId).getLengthOfStay();
        Integer allRooms = roomRepository.getOne(roomId).getNumberOfRooms();
        List<GregorianCalendar> answer = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Reservation reserv = reservationRepository.findByReservationDateAndRoom(date, roomId);
            date.add(Calendar.DATE, 1);
            if (reserv != null) {
                Integer rooms = reserv.getNumberOfRooms();
                if (allRooms.equals(rooms)) answer.add(date);
            }
        }
        return answer;
    }

    @Override
    public List<GregorianCalendar> cancelReservation(Integer tourId, List<GregorianCalendar> date) {
        if (!tourRepository.findById(tourId).isPresent())
            throw new NotFoundException("Tour doesn't exist");

        Integer roomId = tourRepository.getOne(tourId).getRoom().getId();
        Integer allRooms = roomRepository.getOne(roomId).getNumberOfRooms();
        List<GregorianCalendar> answer = new ArrayList<>();
        for (GregorianCalendar gregorianCalendar : date) {
            Reservation reserve = reservationRepository.findByReservationDateAndRoom(gregorianCalendar, roomId);
            Integer rooms = reserve.getNumberOfRooms();
            if (allRooms.equals(rooms)) answer.add(gregorianCalendar);
            rooms--;
            reserve.setNumberOfRooms(rooms);
            update(ReservationConverter.convertEntityToDto(reserve));
        }
        return answer;
    }

    @Override
    public int create(final ReservationDto reservationDto) {
        if (!roomRepository.findById(reservationDto.getRoomId()).isPresent())
            throw new NotFoundException("Room  doesn't exist");
        try {
            Reservation reservation = ReservationConverter.convertDtoToEntity(reservationDto);
            reservation.setRoom(roomRepository.findById(reservationDto.getRoomId()).get());
            reservationRepository.save(reservation);
            return reservation.getId();
        } catch (NoSuchElementException e) {
            throw new BadRequestException("Not valid data");
        }
    }

    @Override
    public int update(ReservationDto reservationDto) {
        if (!roomRepository.findById(reservationDto.getRoomId()).isPresent())
            throw new NotFoundException("Room doesn't exist");
        Reservation reservation = ReservationConverter.convertDtoToEntity(reservationDto);
        reservation.setRoom(roomRepository.findById(reservationDto.getRoomId()).get());
        if (reservation.getId() <= 0)
            throw new BadRequestException("Not valid data");
        if (reservationRepository.findById(reservationDto.getId()).isPresent()) {
            reservationRepository.delete(reservationRepository.findById(reservationDto.getId()).get());
            reservationRepository.save(reservation);
            return reservation.getId();
        } else {
            throw new NotFoundException("Reservation doesn't exist");
        }
    }

    @Override
    public void delete(ReservationDto reservationDto) {
        if (reservationDto.getId() <= 0) throw new BadRequestException("Not valid id");
        if (reservationRepository.findById(reservationDto.getId()).isPresent()) {
            reservationRepository.deleteById(reservationDto.getId());
        } else {
            throw new NotFoundException("Reservation doesn't exist");
        }
    }

    @Override
    public ReservationDto findById(int id) {
        ReservationDto reservationDto;
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (reservationRepository.findById(id).isPresent()) {
            reservationDto = ReservationConverter.convertEntityToDto(reservationRepository.findById(id).get());
        } else {
            throw new NotFoundException("Reservation not found");
        }
        return reservationDto;
    }

    @Override
    public void deleteById(int id) {
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (reservationRepository.findById(id).isPresent()) {
            reservationRepository.deleteById(id);
        } else {
            throw new NotFoundException("Reservation doesn't exist");
        }
    }

    @Override
    public List<ReservationDto> findAll() {

        return reservationRepository.findAll().stream().map((ReservationConverter::convertEntityToDto)).collect(Collectors.toList());
    }
}
