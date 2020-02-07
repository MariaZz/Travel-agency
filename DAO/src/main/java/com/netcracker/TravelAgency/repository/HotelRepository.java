package com.netcracker.TravelAgency.repository;

import com.netcracker.TravelAgency.entity.Country;
import com.netcracker.TravelAgency.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer>, JpaSpecificationExecutor<Hotel> {

    Hotel findByNameAndCountry(String name, Country country);
}
