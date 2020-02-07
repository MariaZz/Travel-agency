package com.netcracker.TravelAgency.controller;

import com.netcracker.TravelAgency.dto.OperatorDto;
import com.netcracker.TravelAgency.service.impl.OperatorServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/operator")
public class OperatorController {
    private OperatorServiceImpl operatorService;
    private static final Logger LOGGER = LoggerFactory.getLogger(OperatorController.class);

    @Autowired
    public OperatorController(final OperatorServiceImpl operatorService) {
        this.operatorService = operatorService;
    }

    @PreAuthorize("hasAnyAuthority('TRAVELAGENT') or hasAuthority('OPERATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOneOperator(@PathVariable("id") Integer id) {
        LOGGER.info("REST request. Path:/operator/{} method: GET.", id);
        final OperatorDto operatorDto = operatorService.findById(id);
        return Objects.isNull(operatorDto) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok().body(operatorDto);
    }


    @PreAuthorize("hasAuthority('OPERATORT')")
    @PutMapping("/")
    public ResponseEntity<?> editOperator(final @Valid @RequestBody OperatorDto operatorDto) {
        LOGGER.info("REST request. Path:/operator/ method: POST. tour: {}", operatorDto);
        operatorService.update(operatorDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PreAuthorize("hasAuthority('OPERATORT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeOperator(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/operator/{} method: DELETE.", id);
        operatorService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyAuthority('TRAVELAGENT') or hasAuthority('OPERATOR')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllOperators() {
        LOGGER.info("REST request. Path:/operator/all method: GET.");
        final List<OperatorDto> operatorDtos = operatorService.findAll();
        return new ResponseEntity<>(operatorDtos, HttpStatus.OK);
    }
}
