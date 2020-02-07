package com.netcracker.TravelAgency.controller;

import com.netcracker.TravelAgency.dto.*;
import com.netcracker.TravelAgency.service.impl.ClientServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    private ClientServiceImpl clientService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    public ClientController(final ClientServiceImpl clientService) {

        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneClient(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/client/{} method: GET.", id);
        final ClientDto clientDto = clientService.findById(id);
        return Objects.isNull(clientDto) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok().body(clientDto);
    }


    @PreAuthorize("hasAuthority('CLIENT')")
    @PutMapping("//")
    public ResponseEntity<?> editClient(final @Valid @RequestBody ClientDto clientDto) {
        LOGGER.info("REST request. Path:/client method: POST. tour: {}", clientDto);
        clientService.update(clientDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PreAuthorize("hasAnyAuthority('TRAVELAGENT') or hasAuthority('OPERATORT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeClient(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/client/{} method: DELETE.", id);
        clientService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyAuthority('TRAVELAGENT') or hasAuthority('OPERATOR')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllClients() {
        LOGGER.info("REST request. Path:/client/all method: GET.");
        final List<ClientDto> clientDtos = clientService.findAll();
        return new ResponseEntity<>(clientDtos, HttpStatus.OK);
    }
}

