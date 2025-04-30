package com.project.DiningReviewAPI.Controller;

import com.project.DiningReviewAPI.Models.User;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;
import java.util.regex.Pattern;


import com.project.DiningReviewAPI.Models.Restaurant;
import com.project.DiningReviewAPI.Repositories.RestaurantRepository;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
    private final RestaurantRepository restaurantRepository;
    private final Pattern zipCodePattern = Pattern.compile("\\d{5}");

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/{restaurant_id}")
    public Restaurant getRestaurantInformation(@PathVariable(name = "restaurant_id") Long id) {
        Optional<Restaurant> restaurantOptional = this.restaurantRepository.findById(id);

        if (!restaurantOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return restaurantOptional.get();
    }

    @GetMapping("/allRestaurants")
    public Iterable<Restaurant> getAllRestaurants(){
        return this.restaurantRepository.findAll();
    }

    @GetMapping("/search")
    public Iterable<Restaurant> getRestaurantsByZipCodeAndAllergy(@RequestParam String zipCode, @RequestParam String allergy){
        Iterable<Restaurant> listOfRestaurants = Collections.EMPTY_LIST;

        validateZipCode(zipCode);

        if(allergy.equalsIgnoreCase("peanut")){
            listOfRestaurants = this.restaurantRepository.findByZipCodeAndPeanutScoreNotNullOrderByPeanutScore(zipCode);
        }else if(allergy.equalsIgnoreCase("dairy")){
            listOfRestaurants = this.restaurantRepository.findByZipCodeAndDairyScoreNotNullOrderByDairyScore(zipCode);
        }else if(allergy.equalsIgnoreCase("eggs")){
            listOfRestaurants = this.restaurantRepository.findByZipCodeAndEggScoreNotNullOrderByEggScore(zipCode);
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return listOfRestaurants;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewRestaurant(@RequestBody Restaurant restaurant) {
        validateRestaurant(restaurant);
        this.restaurantRepository.save(restaurant);
    }



    public void validateRestaurant(Restaurant restaurant){

        if (ObjectUtils.isEmpty(restaurant.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

       validateZipCode(restaurant.getZipCode());

        Optional<Restaurant> existingRestaurant = this.restaurantRepository.findByNameAndZipCode(restaurant.getName(), restaurant.getZipCode());
        if (existingRestaurant.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    private void validateZipCode(String zipcode) {
        if (!zipCodePattern.matcher(zipcode).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}

