package com.netcracker.TravelAgency.repository;

import com.netcracker.TravelAgency.entity.Client;
import com.netcracker.TravelAgency.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>, JpaSpecificationExecutor<Client> {

    void deleteById(Integer id);
    Optional<Client> findByTrip(Trip trip);
}
