package TravelAgency.service.impl;


import com.netcracker.TravelAgency.converter.TourConverter;
import com.netcracker.TravelAgency.entity.Hotel;
import com.netcracker.TravelAgency.entity.Tour;
import com.netcracker.TravelAgency.repository.*;
import com.netcracker.TravelAgency.service.impl.TourServiceImpl;
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
public class TourServiceImplTest {
    @Mock
    private TourRepository tourRepository;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private ArrivalDateRepository arrivalDateRepository;
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private OperatorRepository operatorRepository;
    @InjectMocks
    private TourServiceImpl tourService = new TourServiceImpl(tourRepository, countryRepository,
            hotelRepository, arrivalDateRepository, roomRepository, operatorRepository);

    private Tour tour;
    @Before
    public void setUp() {
        tour = new Tour();
        tour.setId(1);
     /*   tour.setArrivalDateId(2);
        tour.setCost(400);
        tour.setCountryId(2);
        tour.setHotelCategory(Hotel.HotelCategory.FOUR);
        tour.setHotelId(3);
        tour.setLengthOfStay(7);
        tour.setRoomId(1);*/
        tour.setTypeOfFood(Hotel.TypeOfFood.FB);
    }

    @After
    public void tearDown() {
        tour = null;
    }

    @Test
    public void findAll() {
        Mockito.when(tourRepository.findAll()).thenReturn(Collections.singletonList(tour));
        assertEquals(Collections.singletonList(TourConverter.convertEntityToDto(tour)), tourService.findAll());
    }

    @Test
    public void findById() {
        Optional<Tour> optional = Optional.of(tour);
        Mockito.when(tourRepository.findById(1)).thenReturn(optional);
        assertEquals(TourConverter.convertEntityToDto(optional.get()), tourService.findById(1));
    }

    @Test
    public void delete(){
        Mockito.when(tourRepository.findById(1)).thenReturn(Optional.ofNullable(tour));
        tourService.delete(TourConverter.convertEntityToDto(tour));
        Mockito.verify(tourRepository).deleteById(tour.getId());
    }

    @Test
    public void deleteById() {
        Mockito.when(tourRepository.findById(1)).thenReturn(Optional.ofNullable(tour));
        tourService.deleteById(tour.getId());
        Mockito.verify(tourRepository, Mockito.times(1)).deleteById(tour.getId());
    }
}