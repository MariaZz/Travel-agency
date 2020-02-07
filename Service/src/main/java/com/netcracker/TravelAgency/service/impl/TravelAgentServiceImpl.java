package com.netcracker.TravelAgency.service.impl;

import com.netcracker.TravelAgency.converter.TravelAgentConverter;
import com.netcracker.TravelAgency.dto.TravelAgentDto;
import com.netcracker.TravelAgency.entity.Client;
import com.netcracker.TravelAgency.entity.Country;
import com.netcracker.TravelAgency.entity.TravelAgent;
import com.netcracker.TravelAgency.exeption.BadRequestException;
import com.netcracker.TravelAgency.exeption.NotFoundException;
import com.netcracker.TravelAgency.repository.ClientRepository;
import com.netcracker.TravelAgency.repository.CountryRepository;
import com.netcracker.TravelAgency.repository.TravelAgentRepository;
import com.netcracker.TravelAgency.service.interfaces.CrudService;
import com.netcracker.TravelAgency.service.interfaces.TravelAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class TravelAgentServiceImpl implements TravelAgentService, CrudService<TravelAgentDto> {
    private TravelAgentRepository travelAgentRepository;
    private CountryRepository countryRepository;
    private ClientRepository clientRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public TravelAgentServiceImpl(TravelAgentRepository travelAgentRepository, BCryptPasswordEncoder passwordEncoder,
                                  ClientRepository clientRepository, CountryRepository countryRepository) {

        this.passwordEncoder = passwordEncoder;
        this.clientRepository = clientRepository;
        this.countryRepository = countryRepository;
        this.travelAgentRepository = travelAgentRepository;
    }

    @Override
    public List<TravelAgentDto> findAll() {
        return travelAgentRepository.findAll().stream().map(TravelAgentConverter::convertEntityToDto).collect(Collectors.toList());
    }

    @Override
    public TravelAgentDto findById(int id) {
        TravelAgentDto travelAgentDto;
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (travelAgentRepository.findById(id).isPresent()) {
            travelAgentDto = TravelAgentConverter.convertEntityToDto(travelAgentRepository.findById(id).get());
        } else {
            throw new NotFoundException("Travel agent not found");
        }
        return travelAgentDto;
    }

    @Override
    public void delete(TravelAgentDto travelAgentDto) {
        if (travelAgentDto.getId() <= 0) throw new BadRequestException("Not valid id");
        if (travelAgentRepository.findById(travelAgentDto.getId()).isPresent()) {
            travelAgentRepository.deleteById(travelAgentDto.getId());
        } else {
            throw new NotFoundException("Travel agent doesn't exist");
        }
    }

    @Override
    public void deleteById(int id) {
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (travelAgentRepository.findById(id).isPresent()) {
            travelAgentRepository.deleteById(id);
        } else {
            throw new NotFoundException("Travel agent doesn't exist");
        }
    }

    @Override
    public boolean loginAlreadyExist(String login) {
        return travelAgentRepository.findAll().stream().anyMatch(l -> l.getLogin().equals(login));
    }

    @Override
    public int create(TravelAgentDto travelAgentDto) {
        if(travelAgentDto.getClientsId()!=null) {
            for (int id : travelAgentDto.getClientsId()) {
                if (!clientRepository.findById(id).isPresent())
                    throw new NotFoundException("Client doesn't exist");
            }
        }
        for (int id : travelAgentDto.getCountriesId()) {
            if (!countryRepository.findById(id).isPresent())
                throw new NotFoundException("Country doesn't exist");
        }
        try {
            travelAgentDto.setPassword(passwordEncoder.encode(travelAgentDto.getPassword()));
            travelAgentDto.setPassword(travelAgentDto.getPassword());
            TravelAgent travelAgent = TravelAgentConverter.convertDtoToEntity(travelAgentDto);
            List<Country> countries = travelAgentDto.getCountriesId().stream()
                    .map(a -> countryRepository.findById(a).get())
                    .collect(Collectors.toList());
            if(travelAgentDto.getClientsId()!=null) {
                List<Client> clients = travelAgentDto.getClientsId().stream()
                        .map(x -> clientRepository.findById(x).get())
                        .collect(Collectors.toList());
                travelAgent.setClients(clients);
            }
            travelAgent.setCountries(countries);
            travelAgentRepository.save(travelAgent);
            return travelAgent.getId();
        } catch (NoSuchElementException e) {
            throw new BadRequestException("Not valid data");
        }
    }

    @Override
    public int update(TravelAgentDto travelAgentDto) {
        if(travelAgentDto.getClientsId()!=null) {
            for (int id : travelAgentDto.getClientsId()) {
                if (!clientRepository.findById(id).isPresent())
                    throw new NotFoundException("Client doesn't exist");
            }
        }
        for (int id : travelAgentDto.getCountriesId()) {
            if (!countryRepository.findById(id).isPresent())
                throw new NotFoundException("Country doesn't exist");
        }
        if (travelAgentDto.getId() <= 0)
            throw new BadRequestException("Not valid data");
        if (travelAgentRepository.findById(travelAgentDto.getId()).isPresent()) {
            travelAgentDto.setPassword(passwordEncoder.encode(travelAgentDto.getPassword()));
            travelAgentDto.setPassword(travelAgentDto.getPassword());
            TravelAgent travelAgent = TravelAgentConverter.convertDtoToEntity(travelAgentDto);
            List<Country> countries = travelAgentDto.getCountriesId().stream()
                    .map(a -> countryRepository.findById(a).get())
                    .collect(Collectors.toList());
            if(travelAgentDto.getClientsId()!=null) {
                List<Client> clients = travelAgentDto.getClientsId().stream()
                        .map(x -> clientRepository.findById(x).get())
                        .collect(Collectors.toList());
                travelAgent.setClients(clients);
            }
            travelAgent.setCountries(countries);
            travelAgentRepository.save(travelAgent);
            return travelAgent.getId();
        } else {
            throw new NotFoundException("Travel agent doesn't exist");
        }
    }
}
