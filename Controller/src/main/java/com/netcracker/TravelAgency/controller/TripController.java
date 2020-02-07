package com.netcracker.TravelAgency.controller;

import com.netcracker.TravelAgency.dto.*;
import com.netcracker.TravelAgency.service.impl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/trip")
public class TripController {
    private TripServiceImpl tripService;
    private TouristServiceImpl touristService;
    private ReservationServiceImpl reservationService;
    private ArrivalDateServiceImpl arrivalDateService;
    private TourServiceImpl tourService;
    private ClientServiceImpl clientService;
    private RoomServiceImpl roomService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TripController.class);

    @Autowired
    public TripController(final RoomServiceImpl roomService, final ClientServiceImpl clientService, final TripServiceImpl tripService, TouristServiceImpl touristService,
                          ReservationServiceImpl reservationService, ArrivalDateServiceImpl arrivalDateService, TourServiceImpl tourService) {
        this.touristService = touristService;
        this.roomService = roomService;
        this.tourService = tourService;
        this.reservationService = reservationService;
        this.arrivalDateService = arrivalDateService;
        this.clientService = clientService;
        this.tripService = tripService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOneTrip(@PathVariable("id") Integer id) {
        LOGGER.info("REST request. Path:/trip/{} method: GET.", id);
        final TripDto tripDto = tripService.findById(id);
        return Objects.isNull(tripDto) ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                ResponseEntity.ok().body(tripDto);
    }


    @PreAuthorize("hasAnyAuthority('TRAVELAGENT') or hasAuthority('CLIENT')")
    @PutMapping("/payment/{id}")
    public ResponseEntity<?> makeTripPayment(@PathVariable("id") Integer id) {
        LOGGER.info("REST request. Path:/trip/payment/{} method: GET.", id);
        final ClientDto clientDto = clientService.findById(id);
        if (Objects.isNull(clientDto))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            clientService.payClientTripById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }

    @PreAuthorize("hasAnyAuthority('TRAVELAGENT') or hasAuthority('OPERATOR')")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeTrip(@PathVariable("id") int id) {
        LOGGER.info("REST request. Path:/trip/{} method: DELETE.", id);
        ClientDto clientDto = clientService.findByTripId(id);
        clientDto.setPayment(null);
        clientService.update(clientDto);


        GregorianCalendar arrivalDate = tripService.findById(id).getArrivalDate();
        TourDto tourDto = tourService.findById(tripService.findById(id).getTourId());

        List<GregorianCalendar> dates = new ArrayList<>();
        GregorianCalendar date = new GregorianCalendar();
        date.set(arrivalDate.get(Calendar.YEAR), arrivalDate.get(Calendar.MONTH), arrivalDate.get(Calendar.DATE));
        for (int i = 0; i < tourDto.getLengthOfStay(); i++) {
            dates.add(date);
            date.add(Calendar.DATE, 1);
        }
        List<TourDto> tourWithReservation = tourService.findAllWithRoomInTour(tourDto.getId());

        //для всех туров с этой комнатой
        for (TourDto t : tourWithReservation) {
            //отмена резервации
            List<GregorianCalendar> reservationDates = reservationService.cancelReservation(t.getId(), dates);
            //если резервация комнаты была заполнена хотя бы в один из дней отката
            if (reservationDates.size() != 0) {
                List<ArrivalDateDto> allDatesInCountry = arrivalDateService.findByTourIdCountry(t.getId());
                List<Integer> arrivalDatesInTour = t.getArrivalDatesId();
                List<Integer> idDatesInTour = t.getArrivalDatesId();
                //проверяем все дни вылета, которых нет в турах
                for (ArrivalDateDto arrD : allDatesInCountry) {
                    if (!idDatesInTour.contains(arrD.getId())) {
                        List<GregorianCalendar> reserv = reservationService.searchReservation(t.getId(), arrD.getArrivalDate());
                        //если  резервация свободна, то возвращаем день вылета в тур
                        if (reserv.size() == 0) {
                            arrivalDatesInTour.add(arrD.getId());
                            t.setArrivalDatesId(arrivalDatesInTour);
                            tourService.update(t);
                        }
                    }
                }
            }
        }

        tripService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAnyAuthority('TRAVELAGENT') or hasAuthority('OPERATOR')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllTrips() {
        LOGGER.info("REST request. Path:/trip/all method: GET.");
        final List<TripDto> tripDtos = tripService.findAll();
        return new ResponseEntity<>(tripDtos, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('TRAVELAGENT') or hasAuthority('CLIENT')")
    @Transactional
    @PostMapping("/{clientId}/{contacts}/{tourId}/{arrivalDateId}/{touristsPassportData}")
    public ResponseEntity<?> createTrip(@PathVariable("clientId") Integer clientId, @PathVariable("contacts") String contacts, @PathVariable("tourId") Integer tourId, @PathVariable("arrivalDateId") Integer arrivalDateId, @PathVariable("touristsPassportData") String touristsPassportData) {
        LOGGER.info("REST request. Path:/trip/{}/{}/{}/{} method: POST. createTrip", contacts, tourId, arrivalDateId, touristsPassportData);


        ArrivalDateDto arrivalDateDto = arrivalDateService.findById(arrivalDateId);
        TripDto tripDto = tripService.reserveTrip(tourId, arrivalDateDto.getArrivalDate(), new ArrayList<>());
        String delimiter1;
        delimiter1 = ";";
        String[] tourists = touristsPassportData.split(delimiter1);

        List<TouristDto> touristDtos = touristService.getTouristsList(tourists, tripDto);
        List<Integer> touristsId = new ArrayList<>();
        for (TouristDto dto : touristDtos)
            touristsId.add(dto.getId());
        tripDto.setTouristsId(touristsId);
        tripService.create(tripDto);

        RoomDto roomDto = roomService.findById(tourService.findById(tourId).getRoomId());
        int check = reservationService.checkReservation(tourId, roomDto, arrivalDateDto.getArrivalDate());
        if (check == 0) tourService.deleteArrivalDateInTour(tourId, arrivalDateId);

        List<TourDto> tours = tourService.findAllWithRoomInTour(tourId);
        for (TourDto tour : tours) {
            List<GregorianCalendar> reservations = reservationService.searchReservation(tour.getId(), arrivalDateDto.getArrivalDate());
            if (reservations.size() != 0)
                tourService.deleteArrivalDateInTour(tour.getId(), arrivalDateId);
        }
        clientService.reservationTrip(clientId, tripDto.getId(), contacts);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
