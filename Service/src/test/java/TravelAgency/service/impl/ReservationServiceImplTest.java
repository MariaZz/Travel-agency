package TravelAgency.service.impl;

import com.netcracker.TravelAgency.converter.ReservationConverter;
import com.netcracker.TravelAgency.dto.ReservationDto;
import com.netcracker.TravelAgency.entity.Reservation;
import com.netcracker.TravelAgency.repository.ReservationRepository;
import com.netcracker.TravelAgency.repository.RoomRepository;
import com.netcracker.TravelAgency.repository.TourRepository;
import com.netcracker.TravelAgency.service.impl.ReservationServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceImplTest {
    @Mock
    private ReservationRepository reservationRepository;
    @Mock
    private TourRepository tourRepository;
    @Mock
    private RoomRepository roomRepository;
    @InjectMocks
    private ReservationServiceImpl reservationService = new ReservationServiceImpl(tourRepository, reservationRepository, roomRepository);

    private Reservation reservation;

    @Before
    public void setUp() {
        reservation = new Reservation();
        reservation.setId(1);
        reservation.setNumberOfRooms(10);
     //   reservation.setRoomId(1);
        reservation.setReservationDate(new GregorianCalendar(2020, Calendar.FEBRUARY, 25));
    }

    @After
    public void tearDown() {
        reservation = null;
    }

    @Test
    public void findAll() {
        Mockito.when(reservationRepository.findAll()).thenReturn(Collections.singletonList(reservation));
        assertEquals(Collections.singletonList( ReservationConverter.convertEntityToDto(reservation)), reservationService.findAll());
    }

    @Test
    public void findById() {
        Optional<Reservation> optional = Optional.of(reservation);
        Mockito.when(reservationRepository.findById(1)).thenReturn(optional);
        ReservationDto reservationDto1 = ReservationConverter.convertEntityToDto(optional.get());
        ReservationDto reservationDto2 =  reservationService.findById(1);
        assertEquals(reservationDto1,reservationDto2);
    }

    @Test
    public void delete(){
        Mockito.when(reservationRepository.findById(1)).thenReturn(Optional.ofNullable(reservation));
        reservationService.delete(ReservationConverter.convertEntityToDto(reservation));
        Mockito.verify(reservationRepository).deleteById(reservation.getId());
    }

    @Test
    public void deleteById() {
        Mockito.when(reservationRepository.findById(1)).thenReturn(Optional.ofNullable(reservation));
        reservationService.deleteById(reservation.getId());
        Mockito.verify(reservationRepository, Mockito.times(1)).deleteById(reservation.getId());
    }
}
