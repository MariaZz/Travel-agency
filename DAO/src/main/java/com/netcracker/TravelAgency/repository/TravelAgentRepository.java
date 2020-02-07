package com.netcracker.TravelAgency.repository;

import com.netcracker.TravelAgency.entity.TravelAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelAgentRepository extends JpaRepository<TravelAgent, Integer>, JpaSpecificationExecutor<TravelAgent> {
}
