package com.netcracker.TravelAgency.controller;

import com.netcracker.TravelAgency.dto.HotelDto;
import com.netcracker.TravelAgency.service.impl.HotelServiceImpl;
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
@RequestMapping("/hotel")
public class HotelController {

    private HotelServiceImpl hotelService;
    private static final Logger LOGGER = LoggerFactory.getLogger(HotelController.class);

    @Autowired
    public HotelController(final HotelServiceImpl hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneHotel(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/hotel/{} method: GET.", id);
        final HotelDto hotelDto = hotelService.findById(id);
        return Objects.isNull(hotelDto) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok().body(hotelDto);
    }


    @PreAuthorize("hasAuthority('OPERATOR')")
    @PutMapping("/")
    public ResponseEntity<?> editHotel(final @Valid @RequestBody HotelDto hotelDto) {
        LOGGER.info("REST request. Path:/hotel/ method: POST. hotel: {}", hotelDto);
        hotelService.update(hotelDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('OPERATOR')")
    @PostMapping("")
    public ResponseEntity<?> createHotel(final @Valid @RequestBody HotelDto hotelDto) {
        LOGGER.info("REST request. Path:/hotel method: POST. hotel: {}", hotelDto);
        hotelService.create(hotelDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PreAuthorize("hasAuthority('OPERATOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeHotel(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/hotel/{} method: DELETE.", id);
        hotelService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllHotels() {
        LOGGER.info("REST request. Path:/hotel/all method: GET.");
        final List<HotelDto> hotelDtos = hotelService.findAll();
        return new ResponseEntity<>(hotelDtos, HttpStatus.OK);
    }
}
