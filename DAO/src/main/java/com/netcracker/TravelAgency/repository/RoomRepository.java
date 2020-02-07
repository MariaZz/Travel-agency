package com.netcracker.TravelAgency.repository;

import com.netcracker.TravelAgency.entity.Hotel;
import com.netcracker.TravelAgency.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer>, JpaSpecificationExecutor<Room> {
    List<Room> findByHotelAndNumberOfRoomsAfterAndNumberOfSleepingPlacesAfterAndTypeOfRoom(
            Hotel hotel, Integer numOfRoomsMin, Integer numOfSleepingPlacesMin, Room.TypeOfRoom type);
}
