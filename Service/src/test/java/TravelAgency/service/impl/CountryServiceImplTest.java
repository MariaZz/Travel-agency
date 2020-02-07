package TravelAgency.service.impl;


import com.netcracker.TravelAgency.converter.CountryConverter;
import com.netcracker.TravelAgency.entity.Country;
import com.netcracker.TravelAgency.repository.CountryRepository;
import com.netcracker.TravelAgency.repository.TravelAgentRepository;
import com.netcracker.TravelAgency.service.impl.CountryServiceImpl;
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
public class CountryServiceImplTest {
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private TravelAgentRepository travelAgentRepository;
    @InjectMocks
    private CountryServiceImpl countryService = new CountryServiceImpl(countryRepository, travelAgentRepository);

    private Country country;

    @Before
    public void setUp() {
        country = new Country();
        country.setId(1);
        country.setName("Egypt");
    }

    @After
    public void tearDown() {
        country = null;
    }

    @Test
    public void findAll() {
        Mockito.when(countryRepository.findAll()).thenReturn(Collections.singletonList(country));
        assertEquals(Collections.singletonList(CountryConverter.convertEntityToDto(country)), countryService.findAll());
    }

    @Test
    public void findById() {
        Optional<Country> optional = Optional.of(country);
        Mockito.when(countryRepository.findById(1)).thenReturn(optional);
        assertEquals(CountryConverter.convertEntityToDto(optional.get()), countryService.findById(1));
    }

    @Test
    public void deleteById() {
        Mockito.when(countryRepository.findById(1)).thenReturn(Optional.ofNullable(country));
        countryService.deleteById(country.getId());
        Mockito.verify(countryRepository, Mockito.times(1)).deleteById(country.getId());
    }

    @Test
    public void delete() {
        Mockito.when(countryRepository.findById(1)).thenReturn(Optional.ofNullable(country));
        countryService.delete(CountryConverter.convertEntityToDto(country));
        Mockito.verify(countryRepository).deleteById(country.getId());
    }

}
