package com.project.DiningReviewAPI.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.project.DiningReviewAPI.Models.Restaurant;

import java.util.Optional;
import java.util.List;


public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    Optional<Restaurant> findByNameAndZipCode(String name, String zipcode);

//    @Query("SELECT r FROM Restaurant r WHERE r.zipCode = :zipCode AND (r.peanutScore IS NOT NULL OR r.eggScore IS NOT NULL OR r.dairyScore IS NOT NULL)")
//    List<Restaurant> findByZipCodeWithAllergyScores(String zipCode);


    List<Restaurant> findByZipCodeAndPeanutScoreNotNullOrderByPeanutScore(String zipCode);
    List<Restaurant> findByZipCodeAndEggScoreNotNullOrderByEggScore(String zipCode);
    List<Restaurant> findByZipCodeAndDairyScoreNotNullOrderByDairyScore(String zipCode);

}
