package com.project.DiningReviewAPI.Repositories;
import org.springframework.data.repository.CrudRepository;
import com.project.DiningReviewAPI.Models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUserName(String DisplayName);

}
