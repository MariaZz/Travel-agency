package TravelAgency.service.impl;


import com.netcracker.TravelAgency.converter.HotelConverter;
import com.netcracker.TravelAgency.entity.Hotel;
import com.netcracker.TravelAgency.repository.CountryRepository;
import com.netcracker.TravelAgency.repository.HotelRepository;
import com.netcracker.TravelAgency.repository.OperatorRepository;
import com.netcracker.TravelAgency.service.impl.HotelServiceImpl;
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

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class HotelServiceImplTest {
    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private OperatorRepository operatorRepository;
    @Mock
    private CountryRepository countryRepository;
    @InjectMocks
    private HotelServiceImpl hotelService = new HotelServiceImpl(hotelRepository, operatorRepository, countryRepository);

    private Hotel hotel;

    @Before
    public void setUp() {
        hotel = new Hotel();
        hotel.setId(1);
        hotel.setName("Test");
        hotel.setHotelCategory(Hotel.HotelCategory.FOUR);
        hotel.setTypeOfFood(Hotel.TypeOfFood.ALL);
        //hotel.setHotelId(1);
        hotel.setAccommodations("blablabla");
    }

    @After
    public void tearDown() {
        hotel = null;
    }

    @Test
    public void findAll() {
        Mockito.when(hotelRepository.findAll()).thenReturn(Collections.singletonList(hotel));
        assertEquals(Collections.singletonList(HotelConverter.convertEntityToDto(hotel)), hotelService.findAll());
    }

    @Test
    public void findById() {
        Optional<Hotel> optional = Optional.of(hotel);
        Mockito.when(hotelRepository.findById(1)).thenReturn(optional);
        assertEquals(HotelConverter.convertEntityToDto(optional.get()), hotelService.findById(1));
    }

    @Test
    public void delete(){
        Mockito.when(hotelRepository.findById(1)).thenReturn(Optional.ofNullable(hotel));
        hotelService.delete(HotelConverter.convertEntityToDto(hotel));
        Mockito.verify(hotelRepository).deleteById(hotel.getId());
    }

    @Test
    public void deleteById() {
        Mockito.when(hotelRepository.findById(1)).thenReturn(Optional.ofNullable(hotel));
        hotelService.deleteById(hotel.getId());
        Mockito.verify(hotelRepository, Mockito.times(1)).deleteById(hotel.getId());
    }
}
