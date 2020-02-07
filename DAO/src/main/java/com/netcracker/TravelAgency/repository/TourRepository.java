package com.netcracker.TravelAgency.repository;


import com.netcracker.TravelAgency.entity.Country;
import com.netcracker.TravelAgency.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<Tour, Integer>, JpaSpecificationExecutor<Tour> {

    List<Tour> findAllByCountry(Country country);
    Tour getOne(Integer tourId);

    @Query(value = "SELECT * FROM public.\"Tour\" WHERE id= :tId", nativeQuery = true)
    Tour findAllById(@Param("tId") Integer tourId);

    @Query(value = "SELECT * FROM public.\"Tour\" WHERE country_id= :cId AND room_id = :rId", nativeQuery = true)
    List<Tour> findByCountry_IdAndRoom_Id(@Param("cId") Integer cId, @Param("rId") Integer rId);

    @Query(value = "SELECT * FROM public.\"Tour\"  "
            + "WHERE hotelcategory = :hcstart AND  typeoffood = :tfstart AND country_id = :cid AND room_id = :rid AND hotel_id = :hid  AND cost>= :costmin AND cost <= :costmax  AND lengthofstay >= :lenofstaymin AND lengthofstay<= :lenofstaymax",
            nativeQuery = true)
    List<Tour> findByCountryAndHotelAndHotelCategoryAndTypeOfFoodAndLengthOfStayBetweenAndRoom_IdAndCostBetween(
            @Param("hcstart") Integer hotelCategoryFrom,
            @Param("lenofstaymin") Integer lenMin, @Param("lenofstaymax") Integer lenMax,
            @Param("tfstart") Integer typeOfFoodFrom,
            @Param("cid") Integer countryId, @Param("hid") Integer hotelId, @Param("rid") Integer roomId,
            @Param("costmin") Integer costMin, @Param("costmax") Integer costMax);

    @Query(value = "SELECT * FROM travel.\"Tour\" "
            + "WHERE hotelcategory IN (hcStart, hcaEnd)"
            + "AND lengthofstay IN (lenOfStayMin,lenOfStayMax)"
            + "AND typeoffood IN (tfStart, tfEnd)" +
            "AND room_id = rId" +
            "AND hotel_id = hId" +
            "AND cost IN (costMin, costMax)",
            nativeQuery = true)
    List<Tour> findByHotelAndHotelCategoryBetweenAndTypeOfFoodBetweenAndLengthOfStayBetweenAndRoomAndCostBetween(
            @Param("hcStart") Integer hotelCategoryFrom, @Param("hcEnd") Integer hotelCategoryTo,
            @Param("lenOfStayMin") Integer lenMin, @Param("lenOfStayMax") Integer lenMax,
            @Param("tfStart") Integer typeOfFoodFrom, @Param("tfEnd") Integer typeOfFoodTo,
            @Param("hId") Integer hotelId, @Param("rId") Integer roomId,
            @Param("costMin") Integer costMin, @Param("costMax") Integer costMax);

    @Query(value = "SELECT * FROM travel.\"Tour\" "
            + "WHERE hotelcategory IN (hcStart, hcaEnd)"
            + "AND lengthofstay IN (lenOfStayMin,lenOfStayMax)"
            + "AND typeoffood IN (tfStart, tfEnd)" +
            "AND country_id = cId" +
            "AND room_id = rId" +
            "AND cost IN (costMin, costMax)",
            nativeQuery = true)
    List<Tour> findByCountryAndHotelCategoryBetweenAndTypeOfFoodBetweenAndLengthOfStayBetweenAndRoomAndCostBetween(
            @Param("hcStart") Integer hotelCategoryFrom, @Param("hcEnd") Integer hotelCategoryTo,
            @Param("lenOfStayMin") Integer lenMin, @Param("lenOfStayMax") Integer lenMax,
            @Param("tfStart") Integer typeOfFoodFrom, @Param("tfEnd") Integer typeOfFoodTo,
            @Param("cId") Integer countryId, @Param("rId") Integer roomId,
            @Param("costMin") Integer costMin, @Param("costMax") Integer costMax);

    @Query(value = "SELECT * FROM travel.\"Tour\" "
            + "WHERE hotelcategory IN (hcStart, hcaEnd)"
            + "AND lengthofstay IN (lenOfStayMin,lenOfStayMax)"
            + "AND typeoffood IN (tfStart, tfEnd)" +
            "AND room_id = rId" +
            "AND cost IN (costMin, costMax)",
            nativeQuery = true)
    List<Tour> findByHotelCategoryBetweenAndTypeOfFoodBetweenAndLengthOfStayBetweenAndRoomAndCostBetween(
            @Param("hcStart") Integer hotelCategoryFrom, @Param("hcEnd") Integer hotelCategoryTo,
            @Param("lenOfStayMin") Integer lenMin, @Param("lenOfStayMax") Integer lenMax,
            @Param("tfStart") Integer typeOfFoodFrom, @Param("tfEnd") Integer typeOfFoodTo, @Param("rId") Integer roomId,
            @Param("costMin") Integer costMin, @Param("costMax") Integer costMax);


}