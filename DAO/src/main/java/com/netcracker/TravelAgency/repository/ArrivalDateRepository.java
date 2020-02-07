package com.netcracker.TravelAgency.repository;

import com.netcracker.TravelAgency.entity.ArrivalDate;
import com.netcracker.TravelAgency.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.GregorianCalendar;
import java.util.List;

@Repository
public interface ArrivalDateRepository extends JpaRepository<ArrivalDate, Integer>, JpaSpecificationExecutor<ArrivalDate> {


    List<ArrivalDate> findByCountry(Country country);

    List<ArrivalDate> findByCountryAndNumberOfFreePlacesAfter(
             Country country, Integer numberOfFreePlaces);
    List<ArrivalDate> findByArrivalDateBetweenAndNumberOfFreePlacesAfter(
            GregorianCalendar arrivalDateAfter, GregorianCalendar arrivalDateBefore, Integer numberOfFreePlaces);

    ArrivalDate findAllByCountryAndAndArrivalDate(Country country, GregorianCalendar arrivalDate);
    List<ArrivalDate> findAll();
}
