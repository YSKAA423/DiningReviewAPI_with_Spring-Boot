package com.project.DiningReviewAPI.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


import com.project.DiningReviewAPI.Models.DiningReview;
import com.project.DiningReviewAPI.Models.User;
import com.project.DiningReviewAPI.Models.ReviewStatus;

import com.project.DiningReviewAPI.Repositories.DiningReviewRepository;
import com.project.DiningReviewAPI.Repositories.UserRepository;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/dining_review")
public class DiningReviewController {

    private final DiningReviewRepository diningReviewRepository;
    private final UserRepository userRepository;

    public DiningReviewController(DiningReviewRepository diningReviewRepository, UserRepository userRepository){
        this.diningReviewRepository = diningReviewRepository;
        this.userRepository = userRepository;
    }


    @PostMapping
    public void createNewDiningReview(@RequestBody DiningReview diningReview){
        validateDiningReview(diningReview);

        diningReview.setStatus(ReviewStatus.PENDING);
        this.diningReviewRepository.save(diningReview);
    }


    public void validateDiningReview(DiningReview diningReview){

        if(ObjectUtils.isEmpty(diningReview.getUserName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if(ObjectUtils.isEmpty(diningReview.getRestaurantId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        if(ObjectUtils.isEmpty(diningReview.getPeanutScore()) && ObjectUtils.isEmpty(diningReview.getDairyScore()) && ObjectUtils.isEmpty(diningReview.getEggScore()) ){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<User> userOptional = this.userRepository.findByUserName(diningReview.getUserName());
        if (userOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }



    }



}
