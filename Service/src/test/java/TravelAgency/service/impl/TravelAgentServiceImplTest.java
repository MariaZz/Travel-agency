package TravelAgency.service.impl;

import com.netcracker.TravelAgency.converter.TravelAgentConverter;
import com.netcracker.TravelAgency.dto.TravelAgentDto;
import com.netcracker.TravelAgency.entity.TravelAgent;
import com.netcracker.TravelAgency.repository.ClientRepository;
import com.netcracker.TravelAgency.repository.CountryRepository;
import com.netcracker.TravelAgency.repository.TravelAgentRepository;
import com.netcracker.TravelAgency.service.impl.TravelAgentServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class TravelAgentServiceImplTest {
    @Mock
    private TravelAgentRepository travelAgentRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private CountryRepository countryRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private TravelAgentServiceImpl travelAgentService = new TravelAgentServiceImpl(travelAgentRepository, passwordEncoder,
            clientRepository, countryRepository);

    private TravelAgent travelAgent;

    @Before
    public void setUp() {
        travelAgent = new TravelAgent();
        travelAgent.setId(1);
    }

    @After
    public void tearDown() {
        travelAgent = null;
    }

    @Test
    public void findAll() {
        Mockito.when(travelAgentRepository.findAll()).thenReturn(Collections.singletonList(travelAgent));
        assertEquals(Collections.singletonList(TravelAgentConverter.convertEntityToDto(travelAgent)), travelAgentService.findAll());
    }

    @Test
    public void create() {
        TravelAgentDto travelAgentDto = new TravelAgentDto();
        travelAgentDto.setId(2);
        assertEquals(2, travelAgentService.create(travelAgentDto));
    }

    @Test
    public void update() {
        TravelAgentDto travelAgentDto = TravelAgentConverter.convertEntityToDto(travelAgent);
        travelAgentDto.setCountriesId(new ArrayList<>());
        TravelAgent op = TravelAgentConverter.convertDtoToEntity(travelAgentDto);
        Mockito.when(travelAgentRepository.findById(1)).thenReturn(Optional.ofNullable(op));
        assertEquals(1,travelAgentService.update(travelAgentDto));
    }

    @Test
    public void findById(){
        Optional<TravelAgent> optional = Optional.of(travelAgent);
        Mockito.when(travelAgentRepository.findById(1)).thenReturn(optional);
        assertEquals(TravelAgentConverter.convertEntityToDto(optional.get()), travelAgentService.findById(1));
    }

    @Test
    public void deleteById() {
        Mockito.when(travelAgentRepository.findById(1)).thenReturn(Optional.ofNullable(travelAgent));
        travelAgentService.deleteById(travelAgent.getId());
        Mockito.verify(travelAgentRepository, Mockito.times(1)).deleteById(travelAgent.getId());
    }

    @Test
    public void delete(){
        Mockito.when(travelAgentRepository.findById(1)).thenReturn(Optional.ofNullable(travelAgent));
        travelAgentService.delete(TravelAgentConverter.convertEntityToDto(travelAgent));
        Mockito.verify(travelAgentRepository).deleteById(travelAgent.getId());
    }

    @Test
    public void loginAlreadyExist() {
        travelAgent.setLogin("test");
        Mockito.when(travelAgentRepository.findAll()).thenReturn(Collections.singletonList(travelAgent));
        assertTrue(travelAgentService.loginAlreadyExist("test"));
    }
}
