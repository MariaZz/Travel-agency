package com.netcracker.TravelAgency.controller;

import com.netcracker.TravelAgency.dto.*;
import com.netcracker.TravelAgency.service.impl.TravelAgentServiceImpl;
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
@RequestMapping("/travelagent")
public class TravelAgentController {
    private TravelAgentServiceImpl travelAgentService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TravelAgentController.class);

    @Autowired
    public TravelAgentController(final TravelAgentServiceImpl travelAgentService) {
        this.travelAgentService = travelAgentService;
    }

    @PreAuthorize("hasAnyAuthority('TRAVELAGENT') or hasAuthority('OPERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOneTravelAgent(@PathVariable("id") Integer id) {
        LOGGER.info("REST request. Path:/travelAgent/{} method: GET.", id);
        final TravelAgentDto travelAgentDto = travelAgentService.findById(id);
        return Objects.isNull(travelAgentDto) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok().body(travelAgentDto);
    }


    @PreAuthorize("hasAnyAuthority('TRAVELAGENT') or hasAuthority('OPERATOR')")
    @PutMapping("/")
    public ResponseEntity<?> editTravelAgent(final @Valid @RequestBody TravelAgentDto travelAgentDto) {
        LOGGER.info("REST request. Path:/travelAgent/ method: POST. tour: {}", travelAgentDto);
        travelAgentService.update(travelAgentDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PreAuthorize("hasAuthority('OPERATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeTravelAgent(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/travelAgent/{} method: DELETE.", id);
        travelAgentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyAuthority('TRAVELAGENT') or hasAuthority('OPERATOR')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllTravelAgents() {
        LOGGER.info("REST request. Path:/travelagent/all method: GET.");
        final List<TravelAgentDto> travelAgentDtos = travelAgentService.findAll();
        return new ResponseEntity<>(travelAgentDtos, HttpStatus.OK);
    }


}
