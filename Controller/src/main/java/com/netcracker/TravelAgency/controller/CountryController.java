package com.netcracker.TravelAgency.controller;

import com.netcracker.TravelAgency.dto.CountryDto;
import com.netcracker.TravelAgency.service.impl.CountryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/country")
public class CountryController {

    private CountryServiceImpl countryService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

    public CountryController(final CountryServiceImpl countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Integer id) {
        LOGGER.info("REST request. Path:/country/{} method: GET.", id);
        final CountryDto countryDto = countryService.findById(id);
        return Objects.isNull(countryDto) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok().body(countryDto);
    }

    @PreAuthorize("hasAuthority('OPERATOR')")
    @PutMapping("/")
    public ResponseEntity<?> edit(final @Valid @RequestBody CountryDto countryDto) {
        LOGGER.info("REST request. Path:/country/ method: POST. country: {}", countryDto);
        countryService.update(countryDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('OPERATOR')")
    @PostMapping("")
    public ResponseEntity<?> create(final @Valid @RequestBody CountryDto countryDto) {
        LOGGER.info("REST request. Path:/country method: POST. country: {}", countryDto);
        countryService.create(countryDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('OPERATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/country/{} method: DELETE.", id);
        countryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        LOGGER.info("REST request. Path:/country/all method: GET.");
        final List<CountryDto> countryDtos = countryService.findAll();
        return new ResponseEntity<>(countryDtos, HttpStatus.OK);
    }
}
