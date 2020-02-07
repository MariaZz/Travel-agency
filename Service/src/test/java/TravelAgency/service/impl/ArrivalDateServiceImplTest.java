package TravelAgency.service.impl;

import com.netcracker.TravelAgency.converter.ArrivalDateConverter;
import com.netcracker.TravelAgency.dto.ArrivalDateDto;
import com.netcracker.TravelAgency.entity.ArrivalDate;
import com.netcracker.TravelAgency.entity.Country;
import com.netcracker.TravelAgency.repository.ArrivalDateRepository;
import com.netcracker.TravelAgency.repository.CountryRepository;
import com.netcracker.TravelAgency.repository.TourRepository;
import com.netcracker.TravelAgency.service.impl.ArrivalDateServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
@RunWith(MockitoJUnitRunner.class)
public class ArrivalDateServiceImplTest {
    @Mock
    private ArrivalDateRepository arrivalDateRepository;
    @Mock
    private TourRepository tourRepository;
    @Mock
    private CountryRepository countryRepository;
    @InjectMocks
    private ArrivalDateServiceImpl arrivalDateService = new ArrivalDateServiceImpl(arrivalDateRepository, tourRepository, countryRepository);

    private ArrivalDate arrivalDate;

    @Before
    public void setUp() {
        arrivalDate = new ArrivalDate();
        arrivalDate.setId(1);
        arrivalDate.setCountry(new Country());
        arrivalDate.setArrivalDate(new GregorianCalendar(2020, Calendar.MARCH, 2));
        arrivalDate.setNumberOfFreePlaces(100);
    }

    @After
    public void tearDown() {
        arrivalDate = null;
    }

    @Test
    public void findAll() {
        Mockito.when(arrivalDateRepository.findAll()).thenReturn(Collections.singletonList(arrivalDate));
        assertEquals(Collections.singletonList(ArrivalDateConverter.convertEntityToDto(arrivalDate)), arrivalDateService.findAll());
    }

    @Test
    public void findById() {
        Optional<ArrivalDate> optional = Optional.of(arrivalDate);
        Mockito.when(arrivalDateRepository.findById(1)).thenReturn(optional);
        ArrivalDateDto dto = ArrivalDateConverter.convertEntityToDto(optional.get());
        assertEquals(dto, arrivalDateService.findById(1));
    }
    @Test
    public void deleteById(){
        Mockito.when(arrivalDateRepository.findById(1)).thenReturn(Optional.ofNullable(arrivalDate));
        arrivalDateService.deleteById(arrivalDate.getId());
        Mockito.verify(arrivalDateRepository, Mockito.times(1)).deleteById(arrivalDate.getId());
    }
    @Test
    public void delete(){
        Mockito.when(arrivalDateRepository.findById(1)).thenReturn(Optional.ofNullable(arrivalDate));
        arrivalDateService.delete(ArrivalDateConverter.convertEntityToDto(arrivalDate));
        Mockito.verify(arrivalDateRepository).deleteById(arrivalDate.getId());
    }
    @Test
    public void create() {
        ArrivalDateDto arrivalDateDto = new ArrivalDateDto();
        arrivalDateDto.setId(2);
        assertEquals(2, arrivalDateService.create(arrivalDateDto));
    }

    @Test
    public void update() {
        ArrivalDateDto arrivalDateDto = ArrivalDateConverter.convertEntityToDto(arrivalDate);
        arrivalDateDto.setNumberOfFreePlaces(99);
        ArrivalDate cl = ArrivalDateConverter.convertDtoToEntity(arrivalDateDto);
        Mockito.when(arrivalDateRepository.findById(1)).thenReturn(Optional.ofNullable(cl));
        assertEquals(1,arrivalDateService.update(arrivalDateDto));
    }
}

