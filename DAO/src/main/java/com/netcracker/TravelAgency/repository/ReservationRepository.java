package com.netcracker.TravelAgency.repository;

import com.netcracker.TravelAgency.entity.Reservation;
import com.netcracker.TravelAgency.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer>, JpaSpecificationExecutor<Reservation> {


    Optional<Reservation> findFirstByReservationDateAndRoom(GregorianCalendar arrivalDate, Room room);

    List<Reservation> findAllByRoom(Room room);

    List<Reservation> findByReservationDate(GregorianCalendar arrivalDate);


    @Query(value = "SELECT * FROM public.\"Reservation\" WHERE room_id= :rId ", nativeQuery = true)
    List<Reservation> findByRoom( @Param("rId") Integer roomId);



    @Query(value = "SELECT * FROM public.\"Reservation\" WHERE  room_id= :rId AND reservationdate = :arrDate", nativeQuery = true)
    Reservation findByReservationDateAndRoom(@Param("arrDate") GregorianCalendar arrivalDate,
                                             @Param("rId") Integer roomId);


}
