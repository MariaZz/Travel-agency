package com.netcracker.TravelAgency.repository;

import com.netcracker.TravelAgency.entity.AuthorizedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorizedUserRepository extends JpaRepository<AuthorizedUser, Integer>, JpaSpecificationExecutor<AuthorizedUser> {
    Optional<AuthorizedUser> findByLogin(String login);

    Optional<AuthorizedUser> findById(Integer id);
}
