package TravelAgency.service.impl;

import com.netcracker.TravelAgency.converter.OperatorConverter;
import com.netcracker.TravelAgency.dto.OperatorDto;
import com.netcracker.TravelAgency.entity.Operator;
import com.netcracker.TravelAgency.repository.HotelRepository;
import com.netcracker.TravelAgency.repository.OperatorRepository;
import com.netcracker.TravelAgency.repository.TourRepository;
import com.netcracker.TravelAgency.service.impl.OperatorServiceImpl;
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
public class OperatorServiceImplTest {
    @Mock
    private OperatorRepository operatorRepository;
    @Mock
    private HotelRepository hotelRepository;
    @Mock
    private TourRepository tourRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @InjectMocks
    private OperatorServiceImpl operatorService = new OperatorServiceImpl(operatorRepository, hotelRepository, tourRepository, passwordEncoder);

    private Operator operator;

    @Before
    public void setUp() {
        operator = new Operator();
        operator.setId(1);
    }

    @After
    public void tearDown() {
        operator = null;
    }

    @Test
    public void findAll() {
        Mockito.when(operatorRepository.findAll()).thenReturn(Collections.singletonList(operator));
        assertEquals(Collections.singletonList(OperatorConverter.convertEntityToDto(operator)), operatorService.findAll());
    }

    @Test
    public void create() {
        OperatorDto operatorDto = new OperatorDto();
        operatorDto.setId(2);
        assertEquals(2, operatorService.create(operatorDto));
    }

    @Test
    public void update() {
        OperatorDto operatorDto = OperatorConverter.convertEntityToDto(operator);
        operatorDto.setHotelsId(new ArrayList<>());
        Operator op = OperatorConverter.convertDtoToEntity(operatorDto);
        Mockito.when(operatorRepository.findById(1)).thenReturn(Optional.ofNullable(op));
        assertEquals(1,operatorService.update(operatorDto));
    }

    @Test
    public void findById(){
        Optional<Operator> optional = Optional.of(operator);
        Mockito.when(operatorRepository.findById(1)).thenReturn(optional);
        assertEquals(OperatorConverter.convertEntityToDto(optional.get()), operatorService.findById(1));
    }

    @Test
    public void deleteById() {
        Mockito.when(operatorRepository.findById(1)).thenReturn(Optional.ofNullable(operator));
        operatorService.deleteById(operator.getId());
        Mockito.verify(operatorRepository, Mockito.times(1)).deleteById(operator.getId());
    }

    @Test
    public void delete(){
        Mockito.when(operatorRepository.findById(1)).thenReturn(Optional.ofNullable(operator));
        operatorService.delete(OperatorConverter.convertEntityToDto(operator));
        Mockito.verify(operatorRepository).deleteById(operator.getId());
    }

    @Test
    public void loginAlreadyExist() {
        operator.setLogin("test");
        Mockito.when(operatorRepository.findAll()).thenReturn(Collections.singletonList(operator));
        assertTrue(operatorService.loginAlreadyExist("test"));
    }
}
