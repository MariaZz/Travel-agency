package com.netcracker.TravelAgency.service.impl;

import com.netcracker.TravelAgency.converter.ClientConverter;
import com.netcracker.TravelAgency.dto.ClientDto;
import com.netcracker.TravelAgency.entity.Client;
import com.netcracker.TravelAgency.entity.TravelAgent;
import com.netcracker.TravelAgency.entity.Trip;
import com.netcracker.TravelAgency.exeption.BadRequestException;
import com.netcracker.TravelAgency.exeption.NotFoundException;
import com.netcracker.TravelAgency.repository.ClientRepository;
import com.netcracker.TravelAgency.repository.TravelAgentRepository;
import com.netcracker.TravelAgency.repository.TripRepository;
import com.netcracker.TravelAgency.service.interfaces.ClientService;
import com.netcracker.TravelAgency.service.interfaces.CrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService, CrudService<ClientDto> {

    private ClientRepository clientRepository;
    private TripRepository tripRepository;
    private TravelAgentRepository travelAgentRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository, TripRepository tripRepository,
                             TravelAgentRepository travelAgentRepository, BCryptPasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.tripRepository = tripRepository;
        this.passwordEncoder = passwordEncoder;
        this.travelAgentRepository = travelAgentRepository;
    }


    @Override
    public void reservationTrip(final Integer clientId, final Integer tripId, final String contacts) {
        if (!clientRepository.findById(clientId).isPresent() || !tripRepository.findById(tripId).isPresent())
            throw new NotFoundException("Trip or/and client doesn't exist");
        TravelAgent travelAgent = tripRepository.getOne(tripId).getTour().getCountry().getTravelAgent();
        Client client = clientRepository.getOne(clientId);
        client.setTrip(tripRepository.getOne(tripId));
        client.setContacts(contacts);
        client.setTravelAgent(travelAgent);
        client.setPayment(false);
        update(ClientConverter.convertEntityToDto(client));
    }

    @Override
    public int update(final ClientDto clientDto) {
        if ((clientDto.getTravelAgentId() != null && !travelAgentRepository.findById(clientDto.getTravelAgentId()).isPresent())
                || (clientDto.getTripId() != null && !tripRepository.findById(clientDto.getTripId()).isPresent()))
            throw new NotFoundException("Travel agent or/and trip doesn't exist");
        Client client = ClientConverter.convertDtoToEntity(clientDto);
        if (clientDto.getTravelAgentId() != null)
            client.setTravelAgent(travelAgentRepository.findById(clientDto.getTravelAgentId()).get());
        if (clientDto.getTripId() != null)
            client.setTrip(tripRepository.findById(clientDto.getTripId()).get());
        if (clientDto.getId() <= 0)
            throw new BadRequestException("Not valid data");
        if (clientRepository.findById(clientDto.getId()).isPresent()) {
            clientRepository.delete(clientRepository.findById(clientDto.getId()).get());
            clientRepository.save(client);
            return client.getId();
        } else {
            throw new NotFoundException("Client doesn't exist");
        }
    }

    @Override
    public ClientDto findById(final int id) {
        ClientDto clientDto;
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (clientRepository.findById(id).isPresent()) {
            clientDto = ClientConverter.convertEntityToDto(clientRepository.findById(id).get());
        } else {
            throw new NotFoundException("Client not found");
        }
        return clientDto;
    }

    @Override
    public ClientDto findByTripId(final Integer tripId) {
        if (!tripRepository.findById(tripId).isPresent())
            throw new NotFoundException("Trip doesn't exist");
        Trip trip = tripRepository.getOne(tripId);
        Optional<Client> client = clientRepository.findByTrip(trip);
        return client.map(ClientConverter::convertEntityToDto).orElse(null);
    }

    @Override
    public boolean loginAlreadyExist(final String login) {
        return clientRepository.findAll().stream().anyMatch(l -> l.getLogin().equals(login));
    }

    @Override
    public int create(final ClientDto clientDto) {
        if ((clientDto.getTravelAgentId() != null && !travelAgentRepository.findById(clientDto.getTravelAgentId()).isPresent())
                || (clientDto.getTripId() != null && !tripRepository.findById(clientDto.getTripId()).isPresent()))
            throw new NotFoundException("Travel agent or/and trip doesn't exist");
        try {
            clientDto.setPassword(clientDto.getPassword());
            clientDto.setPassword(passwordEncoder.encode(clientDto.getPassword()));
            Client client = ClientConverter.convertDtoToEntity(clientDto);
            if (clientDto.getTravelAgentId() != null)
                client.setTravelAgent(travelAgentRepository.findById(clientDto.getTravelAgentId()).get());
            if (clientDto.getTripId() != null)
                client.setTrip(tripRepository.findById(clientDto.getTripId()).get());
            clientRepository.save(client);
            return client.getId();
        } catch (NoSuchElementException e) {
            throw new BadRequestException("Not valid data");
        }
    }

    @Override
    public void deleteById(final int id) {
        if (id <= 0) throw new BadRequestException("Not valid id");
        if (clientRepository.findById(id).isPresent()) {
            clientRepository.deleteById(id);
        } else {
            throw new NotFoundException("Client doesn't exist");
        }
    }

    @Override
    public void delete(final ClientDto clientDto) {
        if (clientDto.getId() <= 0) throw new BadRequestException("Not valid id");
        if (clientRepository.findById(clientDto.getId()).isPresent()) {
            clientRepository.deleteById(clientDto.getId());
        } else {
            throw new NotFoundException("Client doesn't exist");
        }
    }

    @Override
    public List<ClientDto> findAll() {
        List<Client> arrivalDate = clientRepository.findAll();
        return arrivalDate.stream().map((ClientConverter::convertEntityToDto)).collect(Collectors.toList());
    }

    @Override
    public void payClientTripById(final int id) {
        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            client.get().setPayment(true);
            clientRepository.save(client.get());
        }
    }
}
