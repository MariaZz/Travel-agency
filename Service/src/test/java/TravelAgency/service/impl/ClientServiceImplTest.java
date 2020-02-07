package TravelAgency.service.impl;

import com.netcracker.TravelAgency.converter.ClientConverter;
import com.netcracker.TravelAgency.dto.ClientDto;
import com.netcracker.TravelAgency.entity.Client;
import com.netcracker.TravelAgency.repository.ClientRepository;
import com.netcracker.TravelAgency.repository.TravelAgentRepository;
import com.netcracker.TravelAgency.repository.TripRepository;
import com.netcracker.TravelAgency.service.impl.ClientServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceImplTest {
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private TripRepository tripRepository;
    @Mock
    private TravelAgentRepository travelAgentRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private ClientServiceImpl clientService = new ClientServiceImpl(clientRepository, tripRepository, travelAgentRepository,passwordEncoder);

    private Client client;

    @Before
    public void setUp() {
        client = new Client(1,"1","111","Tom","1234567");
        client.setName("Anna");
        client.setPayment(false);
    }

    @After
    public void tearDown() {
        client = null;
    }

    @Test
    public void findAll() {
        Mockito.when(clientRepository.findAll()).thenReturn(Collections.singletonList(client));
        assertEquals(Collections.singletonList( ClientConverter.convertEntityToDto(client)), clientService.findAll());
    }

    @Test
    public void create() {
        ClientDto clientDto = new ClientDto();
        clientDto.setId(2);
        assertEquals(2, clientService.create(clientDto));
    }

    @Test
    public void update() {
        ClientDto clientDto = ClientConverter.convertEntityToDto(client);
        clientDto.setContacts("1");
        Client cl = ClientConverter.convertDtoToEntity(clientDto);
        Mockito.when(clientRepository.findById(1)).thenReturn(Optional.ofNullable(cl));
        assertEquals(1,clientService.update(clientDto));
    }

    @Test
    public void findById(){
        Optional<Client> optional = Optional.of(client);
        Mockito.when(clientRepository.findById(1)).thenReturn(optional);
        assertEquals(ClientConverter.convertEntityToDto(optional.get()), clientService.findById(1));
    }

    @Test
    public void payClientTripById() {
        clientService.payClientTripById(client.getId());
        Mockito.verify(clientRepository, Mockito.times(1)).findById(client.getId());
    }

    @Test
    public void deleteById() {
        Mockito.when(clientRepository.findById(1)).thenReturn(Optional.ofNullable(client));
        clientService.deleteById(client.getId());
        Mockito.verify(clientRepository, Mockito.times(1)).deleteById(client.getId());
    }

    @Test
    public void delete(){
        Mockito.when(clientRepository.findById(1)).thenReturn(Optional.ofNullable(client));
        clientService.delete(ClientConverter.convertEntityToDto(client));
        Mockito.verify(clientRepository).deleteById(client.getId());
    }

    @Test
    public void loginAlreadyExist() {
        client.setLogin("test");
        Mockito.when(clientRepository.findAll()).thenReturn(Collections.singletonList(client));
        assertTrue(clientService.loginAlreadyExist("test"));
    }

}
