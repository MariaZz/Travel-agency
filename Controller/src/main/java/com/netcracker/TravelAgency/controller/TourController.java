package com.netcracker.TravelAgency.controller;


import com.netcracker.TravelAgency.dto.ArrivalDateDto;
import com.netcracker.TravelAgency.dto.TourDto;
import com.netcracker.TravelAgency.entity.Hotel;
import com.netcracker.TravelAgency.entity.Room;
import com.netcracker.TravelAgency.service.impl.ArrivalDateServiceImpl;
import com.netcracker.TravelAgency.service.impl.TourServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/tour")
public class TourController {

    private TourServiceImpl tourService;
    private ArrivalDateServiceImpl arrivalDateService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TourController.class);

    @Autowired
    public TourController(final TourServiceImpl tourService, final ArrivalDateServiceImpl arrivalDateService) {
        this.tourService = tourService;
        this.arrivalDateService = arrivalDateService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getOneTour(@PathVariable("id") Integer id) {
        LOGGER.info("REST request. Path:/tour/{} method: GET.", id);
        final TourDto tourDto = tourService.findById(id);
        return Objects.isNull(tourDto) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok().body(tourDto);
    }

    @PreAuthorize("hasAuthority('OPERATOR')")
    @PutMapping("/")
    public ResponseEntity<?> editTour(final @Valid @RequestBody TourDto tourDto) {
        LOGGER.info("REST request. Path:/tour/ method: POST. tour: {}", tourDto);
        tourService.update(tourDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('OPERATOR')")
    @PostMapping("")
    public ResponseEntity<?> createTour(final @Valid @RequestBody TourDto tourDto) {
        LOGGER.info("REST request. Path:/operator/tour method: POST. tour: {}", tourDto);
        tourService.create(tourDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('OPERATOR')")
    @DeleteMapping("/tour/{id}")
    public ResponseEntity<?> removeTour(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/tour/{} method: DELETE.", id);
        tourService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TourDto>> getAllTours() {
        LOGGER.info("REST request. Path:/tour/all method: GET.");
        final List<TourDto> tourDtos = tourService.findAll();
        return new ResponseEntity<>(tourDtos, HttpStatus.OK);
    }

    @GetMapping("/country/{id}")
    public ResponseEntity<List<TourDto>> getToursInCountry(@PathVariable("id") Integer id) {
        LOGGER.info("REST request. Path:/tour/country/{} method: GET.", id);
        final List<TourDto> tourDtos = tourService.findAllByCountryId(id);
        return new ResponseEntity<>(tourDtos, HttpStatus.OK);
    }

    @GetMapping("/{arrDateFrom}/{arrDateTo}/{lenFrom}/{lenTo}/{hotelCat}/{typeOfFood}/{countryName}/" +
            "{hotelName}/{numTourists}/{typeOfRoom}/{minCost}/{maxCost}")
    public ResponseEntity<?> getToursByCriteries(@PathVariable("arrDateFrom") String arrDateFrom, @PathVariable("arrDateTo") String arrDateTo,
                                                 @PathVariable("lenFrom") Integer lenFrom, @PathVariable("lenTo") Integer lenTo,
                                                 @PathVariable("hotelCat") Hotel.HotelCategory hatelCat, @PathVariable("typeOfFood") Hotel.TypeOfFood typeOfFood,
                                                 @PathVariable("countryName") String countryName, @PathVariable("hotelName") String hotelName, @PathVariable("numTourists") Integer numTourists,
                                                 @PathVariable("typeOfRoom") Room.TypeOfRoom typeOfRoom, @PathVariable("minCost") Integer minCost, @PathVariable("maxCost") Integer maxCost) {
        GregorianCalendar arrivalDateFrom = new GregorianCalendar();
        GregorianCalendar arrivalDateTo = new GregorianCalendar();
        String delimiter = ".";
        if (arrDateFrom.charAt(4) == '-')
            delimiter = "-";
        final String[] dateFrom = arrDateFrom.split(delimiter);
        arrivalDateFrom.set(Integer.parseInt(dateFrom[0]), Integer.parseInt(dateFrom[1]) - 1, Integer.parseInt(dateFrom[2]));
        final String[] dateTo = arrDateTo.split(delimiter);
        arrivalDateTo.set(Integer.parseInt(dateTo[0]), Integer.parseInt(dateTo[1]) - 1, Integer.parseInt(dateTo[2]));


        List<TourDto> tourDtos = tourService.findByCriteria(arrivalDateFrom, arrivalDateTo, lenFrom, lenTo, hatelCat, typeOfFood, countryName, hotelName, numTourists, typeOfRoom, minCost, maxCost);
        for (TourDto dto : tourDtos) {
            List<Integer> arrivalDateDtos = new ArrayList<>();
            for (ArrivalDateDto date : arrivalDateService.findByTourIdCountry(dto.getId())) {
                if (date.getNumberOfFreePlaces() >= numTourists)
                    arrivalDateDtos.add(date.getId());
            }
            dto.setArrivalDatesId(arrivalDateDtos);
        }
        return new ResponseEntity<>(tourDtos, HttpStatus.OK);
    }
}
