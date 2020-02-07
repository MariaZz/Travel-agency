package com.netcracker.TravelAgency.controller;

import com.netcracker.TravelAgency.dto.ClientDto;
import com.netcracker.TravelAgency.dto.OperatorDto;
import com.netcracker.TravelAgency.dto.TravelAgentDto;
import com.netcracker.TravelAgency.exeption.BadRequestException;
import com.netcracker.TravelAgency.service.impl.ClientServiceImpl;
import com.netcracker.TravelAgency.service.impl.OperatorServiceImpl;
import com.netcracker.TravelAgency.service.impl.TravelAgentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {
    private ClientServiceImpl clientService;
    private TravelAgentServiceImpl travelAgentService;
    private OperatorServiceImpl operatorService;

    @Autowired
    public RegistrationController(ClientServiceImpl clientService, TravelAgentServiceImpl travelAgentService, OperatorServiceImpl operatorService) {
        this.travelAgentService = travelAgentService;
        this.clientService = clientService;
        this.operatorService = operatorService;
    }

    @PostMapping(value = "/client/registration")
    public ResponseEntity registrationClient(@RequestBody ClientDto clientDto) {
        try {
            if (!clientService.loginAlreadyExist(clientDto.getLogin())) {
                clientService.create(clientDto);
                return new ResponseEntity(HttpStatus.CREATED);
            }
        } catch (BadRequestException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/travelagent/registration")
    public ResponseEntity registrationTravelAgent(@RequestBody TravelAgentDto travelAgentDto) {
        try {
            if (!travelAgentService.loginAlreadyExist(travelAgentDto.getLogin())) {
                travelAgentService.create(travelAgentDto);
                return new ResponseEntity(HttpStatus.CREATED);
            }
        } catch (BadRequestException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/operator/registration")
    public ResponseEntity registrationOperator(@RequestBody OperatorDto operatorDto) {
        try {
            if (!operatorService.loginAlreadyExist(operatorDto.getLogin())) {
                operatorService.create(operatorDto);
                return new ResponseEntity(HttpStatus.CREATED);
            }
        } catch (BadRequestException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}