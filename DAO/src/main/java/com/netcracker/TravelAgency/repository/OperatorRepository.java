package com.netcracker.TravelAgency.repository;

import com.netcracker.TravelAgency.entity.Operator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Integer>, JpaSpecificationExecutor<Operator> {
}
