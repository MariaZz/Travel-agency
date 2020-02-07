package com.netcracker.TravelAgency.service.impl;

import com.netcracker.TravelAgency.converter.OperatorConverter;
import com.netcracker.TravelAgency.dto.OperatorDto;
import com.netcracker.TravelAgency.entity.Hotel;
import com.netcracker.TravelAgency.entity.Operator;
import com.netcracker.TravelAgency.entity.Tour;
import com.netcracker.TravelAgency.exeption.BadRequestException;
import com.netcracker.TravelAgency.exeption.NotFoundException;
import com.netcracker.TravelAgency.repository.HotelRepository;
import com.netcracker.TravelAgency.repository.OperatorRepository;
import com.netcracker.TravelAgency.repository.TourRepository;
import com.netcracker.TravelAgency.service.interfaces.CrudService;
import com.netcracker.TravelAgency.service.interfaces.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class OperatorServiceImpl implements OperatorService, CrudService<OperatorDto> {

    private OperatorRepository operatorRepository;
    private HotelRepository hotelRepository;
    private TourRepository tourRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public OperatorServiceImpl(OperatorRepository operatorRepository, HotelRepository hotelRepository,
                               TourRepository tourRepository, BCryptPasswordEncoder passwordEncoder) {

        this.passwordEncoder = passwordEncoder;
        this.operatorRepository = operatorRepository;
        this.hotelRepository = hotelRepository;
        this.tourRepository = tourRepository;
    }

    @Override
    public List<OperatorDto> findAll() {
        return operatorRepository.findAll().stream().map(OperatorConverter::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public OperatorDto findById(int id) {
        OperatorDto operatorDto;
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (operatorRepository.findById(id).isPresent()) {
            operatorDto = OperatorConverter.convertEntityToDto(operatorRepository.findById(id).get());
        } else {
            throw new NotFoundException("Operator not found");
        }
        return operatorDto;
    }

    @Override
    public void delete(OperatorDto operatorDto) {
        if (operatorDto.getId() <= 0) throw new BadRequestException("Not valid id");
        if (operatorRepository.findById(operatorDto.getId()).isPresent()) {
            operatorRepository.deleteById(operatorDto.getId());
        } else {
            throw new NotFoundException("Operator doesn't exist");
        }
    }

    @Override
    public void deleteById(int id) {
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (operatorRepository.findById(id).isPresent()) {
            operatorRepository.deleteById(id);
        } else {
            throw new NotFoundException("Operator doesn't exist");
        }
    }

    @Override
    public boolean loginAlreadyExist(String login) {
        return operatorRepository.findAll().stream().anyMatch(l -> l.getLogin().equals(login));
    }


    @Override
    public int update(OperatorDto operatorDto) {
        if (operatorDto.getHotelsId() != null) {
            for (int id : operatorDto.getHotelsId()) {
                if (!hotelRepository.findById(id).isPresent())
                    throw new NotFoundException("Hotel doesn't exist");
            }
        }
        if (operatorDto.getToursId() != null) {
            for (int id : operatorDto.getToursId()) {
                if (!hotelRepository.findById(id).isPresent())
                    throw new NotFoundException("Tour doesn't exist");
            }
        }
        if (operatorDto.getId() <= 0)
            throw new BadRequestException("Not valid data");
        if (operatorRepository.findById(operatorDto.getId()).isPresent()) {
            operatorDto.setPassword(passwordEncoder.encode(operatorDto.getPassword()));
            Operator operator = OperatorConverter.convertDtoToEntity(operatorDto);
            if (operatorDto.getHotelsId() != null) {
                List<Hotel> hotels = operatorDto.getHotelsId().stream()
                        .map(a -> hotelRepository.findById(a).get())
                        .collect(Collectors.toList());
                operator.setHotels(hotels);
            }
            if (operatorDto.getToursId() != null) {
                List<Tour> tours = operatorDto.getToursId().stream()
                        .map(x -> tourRepository.findById(x).get())
                        .collect(Collectors.toList());
                operator.setTours(tours);
            }
            operatorRepository.delete(operatorRepository.findById(operatorDto.getId()).get());
            operatorRepository.save(operator);
            return operator.getId();
        } else {
            throw new NotFoundException("Operator doesn't exist");
        }
    }

    @Override
    public int create(OperatorDto operatorDto) {
        if (operatorDto.getHotelsId() != null) {
            for (int id : operatorDto.getHotelsId()) {
                if (!hotelRepository.findById(id).isPresent())
                    throw new NotFoundException("Hotel doesn't exist");
            }
        }
        if (operatorDto.getToursId() != null) {
            for (int id : operatorDto.getToursId()) {
                if (!hotelRepository.findById(id).isPresent())
                    throw new NotFoundException("Tour doesn't exist");
            }
        }
        try {
            operatorDto.setPassword(operatorDto.getPassword());
            operatorDto.setPassword(passwordEncoder.encode(operatorDto.getPassword()));
            Operator operator = OperatorConverter.convertDtoToEntity(operatorDto);
            if (operatorDto.getHotelsId() != null) {
                List<Hotel> hotels = operatorDto.getHotelsId().stream()
                        .map(a -> hotelRepository.findById(a).get())
                        .collect(Collectors.toList());
                operator.setHotels(hotels);
            }
            if (operatorDto.getToursId() != null) {
                List<Tour> tours = operatorDto.getToursId().stream()
                        .map(x -> tourRepository.findById(x).get())
                        .collect(Collectors.toList());
                operator.setTours(tours);
            }
            operatorRepository.save(operator);
            return operator.getId();
        } catch (NoSuchElementException e) {
            throw new BadRequestException("Not valid data");
        }
    }
}
