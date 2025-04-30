package com.project.DiningReviewAPI.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;


import com.project.DiningReviewAPI.Models.DiningReview;
import com.project.DiningReviewAPI.Models.Restaurant;
import com.project.DiningReviewAPI.Models.ReviewStatus;
import com.project.DiningReviewAPI.Models.AdminReviewAction;

import com.project.DiningReviewAPI.Repositories.DiningReviewRepository;
import com.project.DiningReviewAPI.Repositories.UserRepository;
import com.project.DiningReviewAPI.Repositories.RestaurantRepository;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final DiningReviewRepository diningReviewRepository;
    private final UserRepository  userRepository;
    private final RestaurantRepository restaurantRepository;
    private final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    public AdminController(DiningReviewRepository diningReviewRepository, UserRepository  userRepository, RestaurantRepository restaurantRepository){
        this.diningReviewRepository = diningReviewRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/reviews")
    public List<DiningReview> getDiningReviewByStatus(@RequestParam String status){
        ReviewStatus reviewStatus;

        try{
            reviewStatus = ReviewStatus.valueOf(status.toUpperCase());
        }catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return this.diningReviewRepository.findByStatus(reviewStatus);

    }

    @PutMapping("/reviews/{review_id}")
    public void changeStatusOfDiningReview(@PathVariable("review_id") Long reviewId, @RequestBody AdminReviewAction adminReviewAction){
        Optional<DiningReview> diningReviewOptional = this.diningReviewRepository.findById(reviewId);
        if(diningReviewOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }


        DiningReview diningReview = diningReviewOptional.get();

        Optional<Restaurant> restaurantOptional = this.restaurantRepository.findById(diningReview.getRestaurantId());
        if(restaurantOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (adminReviewAction.isAcceptDiningReview()){
            diningReview.setStatus(ReviewStatus.ACCEPTED);
        }else {
            diningReview.setStatus(ReviewStatus.REJECTED);
        }

        this.diningReviewRepository.save(diningReview);

        if (diningReview.getStatus() == ReviewStatus.ACCEPTED) {
            updateRestaurantReviewScores(restaurantOptional.get());
        }

    }

    public void updateRestaurantReviewScores(Restaurant restaurant){
        List<DiningReview> acceptedDiningReviews = this.diningReviewRepository.findByRestaurantIdAndStatus(restaurant.getId(), ReviewStatus.ACCEPTED);

        if (acceptedDiningReviews.isEmpty()) {
            // Set scores to null or zero when no reviews are accepted
            restaurant.setOverallScore(null);
            restaurant.setPeanutScore(null);
            restaurant.setEggScore(null);
            restaurant.setDairyScore(null);
            this.restaurantRepository.save(restaurant);
            return; // Exit the method as no averages can be calculated
        }

        int peanutSum = 0;
        int peanutCount = 0;
        int dairySum = 0;
        int dairyCount = 0;
        int eggSum = 0;
        int eggCount = 0;

        for(DiningReview review: acceptedDiningReviews){
            if(!ObjectUtils.isEmpty(review.getPeanutScore())){
                peanutSum += review.getPeanutScore();
                peanutCount++;
            }

            if(!ObjectUtils.isEmpty(review.getDairyScore())){
                dairySum += review.getDairyScore();
                dairyCount++;
            }

            if(!ObjectUtils.isEmpty(review.getEggScore())){
                eggSum += review.getEggScore();
                eggCount++;
            }

        }

        if ((peanutCount + eggCount + dairyCount) > 0) {
            double overallScore = (double) (peanutSum + eggSum + dairySum) / (peanutCount + eggCount + dairyCount);
            restaurant.setOverallScore(decimalFormat.format(overallScore));
        }else{
            restaurant.setOverallScore(null);
        }

        if(peanutCount > 0){
            double avgPeanutScore = (double) peanutSum / peanutCount;
            restaurant.setPeanutScore(decimalFormat.format(avgPeanutScore));
        }

        if(eggCount > 0){
            double avgEggScore = (double) eggSum / eggCount;
            restaurant.setEggScore(decimalFormat.format(avgEggScore));
        }

        if(dairyCount > 0){
            double avgDairyScore = (double) dairySum / dairyCount;
            restaurant.setDairyScore(decimalFormat.format(avgDairyScore));
        }

        this.restaurantRepository.save(restaurant);
    }
}
