package com.netcracker.TravelAgency.repository;

import com.netcracker.TravelAgency.entity.Tourist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TouristRepository extends JpaRepository<Tourist, Integer>, JpaSpecificationExecutor<Tourist> {
}