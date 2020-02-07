package TravelAgency.service.impl;

import com.netcracker.TravelAgency.converter.RoomConverter;
import com.netcracker.TravelAgency.entity.Room;
import com.netcracker.TravelAgency.repository.HotelRepository;
import com.netcracker.TravelAgency.repository.RoomRepository;
import com.netcracker.TravelAgency.service.impl.RoomServiceImpl;
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
public class RoomServiceImplTest {
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private HotelRepository hotelRepository;
    @InjectMocks
    private RoomServiceImpl roomService = new RoomServiceImpl(roomRepository, hotelRepository);

    private Room room;

    @Before
    public void setUp() {
        room = new Room();
        room.setId(1);
    //    room.setHotelId(1);
        room.setNumberOfSleepingPlaces(2);
   //     room.setReservationId(1);
        room.setTypeOfRoom(Room.TypeOfRoom.STANDARD);
    }

    @After
    public void tearDown() {
        room = null;
    }

    @Test
    public void findAll() {
        Mockito.when(roomRepository.findAll()).thenReturn(Collections.singletonList(room));
        assertEquals(Collections.singletonList( RoomConverter.convertEntityToDto(room)), roomService.findAll());
    }

    @Test
    public void findById() {
        Optional<Room> optional = Optional.of(room);
        Mockito.when(roomRepository.findById(1)).thenReturn(optional);
        assertEquals(RoomConverter.convertEntityToDto(optional.get()), roomService.findById(1));
    }

    @Test
    public void delete(){
        Mockito.when(roomRepository.findById(1)).thenReturn(Optional.ofNullable(room));
        roomService.delete(RoomConverter.convertEntityToDto(room));
        Mockito.verify(roomRepository).deleteById(room.getId());
    }

    @Test
    public void deleteById() {
        Mockito.when(roomRepository.findById(1)).thenReturn(Optional.ofNullable(room));
        roomService.deleteById(room.getId());
        Mockito.verify(roomRepository, Mockito.times(1)).deleteById(room.getId());
    }
}