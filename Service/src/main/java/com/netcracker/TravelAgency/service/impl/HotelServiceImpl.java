package com.netcracker.TravelAgency.service.impl;

import com.netcracker.TravelAgency.converter.HotelConverter;
import com.netcracker.TravelAgency.dto.HotelDto;
import com.netcracker.TravelAgency.entity.Hotel;
import com.netcracker.TravelAgency.exeption.BadRequestException;
import com.netcracker.TravelAgency.exeption.NotFoundException;
import com.netcracker.TravelAgency.repository.CountryRepository;
import com.netcracker.TravelAgency.repository.HotelRepository;
import com.netcracker.TravelAgency.repository.OperatorRepository;
import com.netcracker.TravelAgency.service.interfaces.CrudService;
import com.netcracker.TravelAgency.service.interfaces.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService, CrudService<HotelDto> {

    private HotelRepository hotelRepository;
    private OperatorRepository operatorRepository;
    private CountryRepository countryRepository;

    @Autowired
    public HotelServiceImpl(HotelRepository hotelRepository, OperatorRepository operatorRepository, CountryRepository countryRepository) {

        this.hotelRepository = hotelRepository;
        this.countryRepository = countryRepository;
        this.operatorRepository = operatorRepository;
    }


    @Override
    public int create(final HotelDto hotelDto) {
        if (!operatorRepository.findById(hotelDto.getOperatorId()).isPresent() || !countryRepository.findById(hotelDto.getCountryId()).isPresent())
            throw new NotFoundException("Operator or/and country doesn't exist");
        try {
            Hotel hotel = HotelConverter.convertDtoToEntity(hotelDto);
            hotel.setOperator(operatorRepository.findById(hotelDto.getOperatorId()).get());
            hotel.setCountry(countryRepository.findById(hotelDto.getCountryId()).get());
            hotelRepository.save(hotel);
            return hotel.getId();
        } catch (NoSuchElementException e) {
            throw new BadRequestException("Not valid data");
        }
    }

    @Override
    public int update(HotelDto hotelDto) {
        if (!operatorRepository.findById(hotelDto.getOperatorId()).isPresent() || !countryRepository.findById(hotelDto.getCountryId()).isPresent())
            throw new NotFoundException("Operator or/and country doesn't exist");
        Hotel hotel = HotelConverter.convertDtoToEntity(hotelDto);
        hotel.setOperator(operatorRepository.findById(hotelDto.getOperatorId()).get());
        hotel.setCountry(countryRepository.findById(hotelDto.getCountryId()).get());
        if (hotel.getId() <= 0)
            throw new BadRequestException("Not valid data");
        if (hotelRepository.findById(hotelDto.getId()).isPresent()) {
            hotelRepository.delete(hotelRepository.findById(hotelDto.getId()).get());
            hotelRepository.save(hotel);
            return hotel.getId();
        } else {
            throw new NotFoundException("Hotel doesn't exist");
        }
    }

    @Override
    public List<HotelDto> findAll() {
        return hotelRepository.findAll().stream().map((HotelConverter::convertEntityToDto)).collect(Collectors.toList());
    }

    @Override
    public HotelDto findById(int id) {
        HotelDto hotelDto;
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (hotelRepository.findById(id).isPresent()) {
            hotelDto = HotelConverter.convertEntityToDto(hotelRepository.findById(id).get());
        } else {
            throw new NotFoundException("Hotel not found");
        }
        return hotelDto;
    }

    @Override
    public void delete(HotelDto hotelDto) {
        if (hotelDto.getId() <= 0) throw new BadRequestException("Not valid id");
        if (hotelRepository.findById(hotelDto.getId()).isPresent()) {
            hotelRepository.deleteById(hotelDto.getId());
        } else {
            throw new NotFoundException("Hotel doesn't exist");
        }
    }

    @Override
    public void deleteById(int id) {
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (hotelRepository.findById(id).isPresent()) {
            hotelRepository.deleteById(id);
        } else {
            throw new NotFoundException("Hotel doesn't exist");
        }
    }
}
