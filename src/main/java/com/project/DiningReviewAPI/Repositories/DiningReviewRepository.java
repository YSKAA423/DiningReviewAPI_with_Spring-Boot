package com.project.DiningReviewAPI.Repositories;
import com.project.DiningReviewAPI.Models.ReviewStatus;
import org.springframework.data.repository.CrudRepository;
import com.project.DiningReviewAPI.Models.DiningReview;

import java.util.List;

public interface DiningReviewRepository extends CrudRepository<DiningReview, Long> {
    List<DiningReview> findByStatus(ReviewStatus reviewStatus);

    List<DiningReview> findByRestaurantIdAndStatus(Long restaurantId, ReviewStatus reviewStatus);
}
