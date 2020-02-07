package com.netcracker.TravelAgency.service.impl;

import com.netcracker.TravelAgency.converter.RoomConverter;
import com.netcracker.TravelAgency.dto.RoomDto;
import com.netcracker.TravelAgency.entity.Room;
import com.netcracker.TravelAgency.exeption.BadRequestException;
import com.netcracker.TravelAgency.exeption.NotFoundException;
import com.netcracker.TravelAgency.repository.HotelRepository;
import com.netcracker.TravelAgency.repository.RoomRepository;
import com.netcracker.TravelAgency.service.interfaces.CrudService;
import com.netcracker.TravelAgency.service.interfaces.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService, CrudService<RoomDto> {
    private RoomRepository roomRepository;
    private HotelRepository hotelRepository;

    @Autowired
    public RoomServiceImpl(RoomRepository roomRepository, HotelRepository hotelRepository) {

        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    @Override
    public List<RoomDto> findAll() {
        return roomRepository.findAll().stream().map((RoomConverter::convertEntityToDto)).collect(Collectors.toList());
    }

    @Override
    public int create(final RoomDto roomDto) {
        if (!hotelRepository.findById(roomDto.getHotelId()).isPresent())
            throw new NotFoundException("Hotel  doesn't exist");
        try {
            Room room = RoomConverter.convertDtoToEntity(roomDto);
            room.setHotel(hotelRepository.findById(roomDto.getHotelId()).get());
            roomRepository.save(room);
            return room.getId();
        } catch (NoSuchElementException e) {
            throw new BadRequestException("Not valid data");
        }
    }

    @Override
    public int update(RoomDto roomDto) {
        if (!hotelRepository.findById(roomDto.getHotelId()).isPresent())
            throw new NotFoundException("Hotel doesn't exist");
        Room room = RoomConverter.convertDtoToEntity(roomDto);
        room.setHotel(hotelRepository.findById(roomDto.getHotelId()).get());
        if (room.getId() <= 0)
            throw new BadRequestException("Not valid data");
        if (roomRepository.findById(roomDto.getId()).isPresent()) {
            roomRepository.delete(roomRepository.findById(roomDto.getId()).get());
            roomRepository.save(room);
            return room.getId();
        } else {
            throw new NotFoundException("Room doesn't exist");
        }
    }

    @Override
    public void delete(RoomDto roomDto) {
        if (roomDto.getId() <= 0) throw new BadRequestException("Not valid id");
        if (roomRepository.findById(roomDto.getId()).isPresent()) {
            roomRepository.deleteById(roomDto.getId());
        } else {
            throw new NotFoundException("Room doesn't exist");
        }
    }

    @Override
    public RoomDto findById(int id) {
        RoomDto roomDto;
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (roomRepository.findById(id).isPresent()) {
            roomDto = RoomConverter.convertEntityToDto(roomRepository.findById(id).get());
        } else {
            throw new NotFoundException("Room not found");
        }
        return roomDto;
    }

    @Override
    public void deleteById(int id) {
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (roomRepository.findById(id).isPresent()) {
            roomRepository.deleteById(id);
        } else {
            throw new NotFoundException("Room doesn't exist");
        }
    }
}
