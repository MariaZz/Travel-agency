package TravelAgency.service.impl;


import com.netcracker.TravelAgency.converter.TripConverter;
import com.netcracker.TravelAgency.entity.Trip;
import com.netcracker.TravelAgency.repository.TourRepository;
import com.netcracker.TravelAgency.repository.TouristRepository;
import com.netcracker.TravelAgency.repository.TripRepository;
import com.netcracker.TravelAgency.service.impl.TripServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceImplTest {
    @Mock
    private TripRepository tripRepository;
    @Mock
    private TourRepository tourRepository;
    @Mock
    private TouristRepository touristRepository;
    @InjectMocks
    private TripServiceImpl tripService = new TripServiceImpl(tripRepository, tourRepository, touristRepository);

    private Trip trip;

    @Before
    public void setUp() {
        trip = new Trip();
        trip.setId(1);
    }

    @After
    public void tearDown() {
        trip = null;
    }

    @Test
    public void findAll() {
        Mockito.when(tripRepository.findAll()).thenReturn(Collections.singletonList(trip));
        assertEquals(Collections.singletonList(TripConverter.convertEntityToDto(trip)), tripService.findAll());
    }

    @Test
    public void findById() {
        Optional<Trip> optional = Optional.of(trip);
        Mockito.when(tripRepository.findById(1)).thenReturn(optional);
        assertEquals(TripConverter.convertEntityToDto(optional.get()), tripService.findById(1));
    }

    @Test
    public void delete(){
        Mockito.when(tripRepository.findById(1)).thenReturn(Optional.ofNullable(trip));
        tripService.delete(TripConverter.convertEntityToDto(trip));
        Mockito.verify(tripRepository).deleteById(trip.getId());
    }

    @Test
    public void deleteById() {
        Mockito.when(tripRepository.findById(1)).thenReturn(Optional.ofNullable(trip));
        tripService.deleteById(trip.getId());
        Mockito.verify(tripRepository, Mockito.times(1)).deleteById(trip.getId());
    }
}