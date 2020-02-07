package com.netcracker.TravelAgency.service.impl;

import com.netcracker.TravelAgency.converter.TouristConverter;
import com.netcracker.TravelAgency.dto.TouristDto;
import com.netcracker.TravelAgency.dto.TripDto;
import com.netcracker.TravelAgency.entity.Tourist;
import com.netcracker.TravelAgency.exeption.BadRequestException;
import com.netcracker.TravelAgency.exeption.NotFoundException;
import com.netcracker.TravelAgency.repository.TouristRepository;
import com.netcracker.TravelAgency.repository.TripRepository;
import com.netcracker.TravelAgency.service.interfaces.CrudService;
import com.netcracker.TravelAgency.service.interfaces.TouristService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TouristServiceImpl implements TouristService, CrudService<TouristDto> {
    private TouristRepository touristRepository;
    private TripRepository tripRepository;

    @Autowired
    public TouristServiceImpl(TouristRepository touristRepository, TripRepository tripRepository) {

        this.touristRepository = touristRepository;
        this.tripRepository = tripRepository;
    }

    @Override
    public List<TouristDto> getTouristsList(String[] tourists, TripDto tripDto) {
        if (!tripRepository.findById(tripDto.getId()).isPresent())
            throw new NotFoundException("Trip doesn't exist");

        List<TouristDto> touristDtos = new ArrayList<>();
        for (String s : tourists) {
            TouristDto dto = new TouristDto();
            dto.setTripId(tripDto.getId());
            dto.setPassportData(s);
            int id = create(dto);
            dto.setId(id);
            update(dto);
            touristDtos.add(dto);
        }
        return touristDtos;
    }

    @Override
    public List<TouristDto> findAll() {
        return touristRepository.findAll().stream().map((TouristConverter::convertEntityToDto)).collect(Collectors.toList());
    }

    @Override
    public int create(final TouristDto touristDto) {
        if (touristDto.getTripId() != null && !tripRepository.findById(touristDto.getTripId()).isPresent())
            throw new NotFoundException("Trip doesn't exist");
        try {
            Tourist tourist = TouristConverter.convertDtoToEntity(touristDto);
            if (touristDto.getTripId() != null)
                tourist.setTrip(tripRepository.findById(touristDto.getTripId()).get());
            touristRepository.save(tourist);
            return tourist.getId();
        } catch (NoSuchElementException e) {
            throw new BadRequestException("Not valid data");
        }
    }

    @Override
    public int update(TouristDto touristDto) {
        if (touristDto.getTripId() != null && !tripRepository.findById(touristDto.getTripId()).isPresent())
            throw new NotFoundException("Trip doesn't exist");
        Tourist tourist = TouristConverter.convertDtoToEntity(touristDto);
        if (touristDto.getTripId() != null)
            tourist.setTrip(tripRepository.findById(touristDto.getTripId()).get());
        if (tourist.getId() <= 0)
            throw new BadRequestException("Not valid data");
        if (touristRepository.findById(touristDto.getId()).isPresent()) {
            touristRepository.delete(touristRepository.findById(touristDto.getId()).get());
            touristRepository.save(tourist);
            return tourist.getId();
        } else {
            throw new NotFoundException("Tourist doesn't exist");
        }
    }

    @Override
    public void delete(TouristDto touristDto) {
        if (touristDto.getId() <= 0) throw new BadRequestException("Not valid id");
        if (touristRepository.findById(touristDto.getId()).isPresent()) {
            touristRepository.deleteById(touristDto.getId());
        } else {
            throw new NotFoundException("Tourist doesn't exist");
        }
    }

    @Override
    public TouristDto findById(int id) {
        TouristDto touristDto;
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (touristRepository.findById(id).isPresent()) {
            touristDto = TouristConverter.convertEntityToDto(touristRepository.findById(id).get());
        } else {
            throw new NotFoundException("Tourist not found");
        }
        return touristDto;
    }

    @Override
    public void deleteById(int id) {
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (touristRepository.findById(id).isPresent()) {
            touristRepository.deleteById(id);
        } else {
            throw new NotFoundException("Tourist doesn't exist");
        }
    }
}
