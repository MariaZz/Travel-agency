package TravelAgency.service.impl;

import com.netcracker.TravelAgency.converter.TouristConverter;
import com.netcracker.TravelAgency.entity.Tourist;
import com.netcracker.TravelAgency.repository.TouristRepository;
import com.netcracker.TravelAgency.repository.TripRepository;
import com.netcracker.TravelAgency.service.impl.TouristServiceImpl;
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
public class TouristServiceImplTest {
    @Mock
    private TouristRepository touristRepository;
    @Mock
    private TripRepository tripRepository;
    @InjectMocks
    private TouristServiceImpl touristService = new TouristServiceImpl(touristRepository, tripRepository);

    private Tourist tourist;

    @Before
    public void setUp() {
        tourist = new Tourist();
        tourist.setId(1);
        tourist.setPassportData("123");
    }

    @After
    public void tearDown() {
        tourist = null;
    }

    @Test
    public void findAll() {
        Mockito.when(touristRepository.findAll()).thenReturn(Collections.singletonList(tourist));
        assertEquals(Collections.singletonList(TouristConverter.convertEntityToDto(tourist)), touristService.findAll());
    }

    @Test
    public void findById() {
        Optional<Tourist> optional = Optional.of(tourist);
        Mockito.when(touristRepository.findById(1)).thenReturn(optional);
        assertEquals(TouristConverter.convertEntityToDto(optional.get()), touristService.findById(1));
    }

    @Test
    public void delete(){
        Mockito.when(touristRepository.findById(1)).thenReturn(Optional.ofNullable(tourist));
        touristService.delete(TouristConverter.convertEntityToDto(tourist));
        Mockito.verify(touristRepository).deleteById(tourist.getId());
    }

    @Test
    public void deleteById() {
        Mockito.when(touristRepository.findById(1)).thenReturn(Optional.ofNullable(tourist));
        touristService.deleteById(tourist.getId());
        Mockito.verify(touristRepository, Mockito.times(1)).deleteById(tourist.getId());
    }
}
