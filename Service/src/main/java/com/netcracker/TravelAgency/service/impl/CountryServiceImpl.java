package com.netcracker.TravelAgency.service.impl;


import com.netcracker.TravelAgency.converter.CountryConverter;
import com.netcracker.TravelAgency.dto.CountryDto;
import com.netcracker.TravelAgency.entity.Country;
import com.netcracker.TravelAgency.exeption.BadRequestException;
import com.netcracker.TravelAgency.exeption.NotFoundException;
import com.netcracker.TravelAgency.repository.CountryRepository;
import com.netcracker.TravelAgency.repository.TravelAgentRepository;
import com.netcracker.TravelAgency.service.interfaces.CountryService;
import com.netcracker.TravelAgency.service.interfaces.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
public class CountryServiceImpl implements CountryService, CrudService<CountryDto> {

    private CountryRepository countryRepository;
    private TravelAgentRepository travelAgentRepository;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, TravelAgentRepository travelAgentRepository) {

        this.countryRepository = countryRepository;
        this.travelAgentRepository = travelAgentRepository;
    }

    @Override
    public int create(final CountryDto countryDto) {
        if (countryDto.getTravelAgentId()!=null && !travelAgentRepository.findById(countryDto.getTravelAgentId()).isPresent())
            throw new NotFoundException("Travel agent doesn't exist");
        try {
            Country country = CountryConverter.convertDtoToEntity(countryDto);
            if(countryDto.getTravelAgentId()!=null)
            country.setTravelAgent(travelAgentRepository.findById(countryDto.getTravelAgentId()).get());
            countryRepository.save(country);
            return country.getId();
        } catch (NoSuchElementException e) {
            throw new BadRequestException("Not valid data");
        }
    }

    @Override
    public int update(CountryDto countryDto) {
        if (countryDto.getTravelAgentId()!=null && !travelAgentRepository.findById(countryDto.getTravelAgentId()).isPresent())
            throw new NotFoundException("Travel agent doesn't exist");
        Country country = CountryConverter.convertDtoToEntity(countryDto);
        if(countryDto.getTravelAgentId()!=null)
        country.setTravelAgent(travelAgentRepository.findById(countryDto.getTravelAgentId()).get());
        if (country.getId() <= 0)
            throw new BadRequestException("Not valid data");
        if (countryRepository.findById(countryDto.getId()).isPresent()) {
            countryRepository.delete(countryRepository.findById(countryDto.getId()).get());
            countryRepository.save(country);
            return country.getId();
        } else {
            throw new NotFoundException("Country doesn't exist");
        }
    }

    @Override
    public void delete(CountryDto countryDto) {
        if (countryDto.getId() <= 0) throw new BadRequestException("Not valid id");
        if (countryRepository.findById(countryDto.getId()).isPresent()) {
            countryRepository.deleteById(countryDto.getId());
        } else {
            throw new NotFoundException("Country doesn't exist");
        }
    }

    @Override
    public CountryDto findById(int id) {
        CountryDto countryDto;
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (countryRepository.findById(id).isPresent()) {
            countryDto = CountryConverter.convertEntityToDto(countryRepository.findById(id).get());
        } else {
            throw new NotFoundException("Country not found");
        }
        return countryDto;
    }

    @Override
    public void deleteById(int id) {
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (countryRepository.findById(id).isPresent()) {
            countryRepository.deleteById(id);
        } else {
            throw new NotFoundException("Country doesn't exist");
        }
    }

    @Override
    public List<CountryDto> findAll() {
        return countryRepository.findAll().stream().map((CountryConverter::convertEntityToDto)).collect(Collectors.toList());
    }
}
