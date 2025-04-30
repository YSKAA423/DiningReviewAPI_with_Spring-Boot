package com.project.DiningReviewAPI.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


import com.project.DiningReviewAPI.Models.User;

import com.project.DiningReviewAPI.Repositories.UserRepository;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @GetMapping("/{display_name}")
    public User getUserInformation(@PathVariable(name = "display_name") String displayName){
        Optional<User> userOptional = this.userRepository.findByUserName(displayName);

        if(!userOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return userOptional.get();
    }

    @PostMapping
    public void createNewUser(@RequestBody User user){
        checkUserName(user.getUserName());
        validateUser(user);
        this.userRepository.save(user);
    }

    @PutMapping("/{userName}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void updateUserInformation(@PathVariable String userName, @RequestBody User user){

        checkUserName(userName);

        Optional<User> userOptional = this.userRepository.findByUserName(userName);

        if(!userOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        User userToBeUpdated = userOptional.get();

//        if(!ObjectUtils.isEmpty(userToBeUpdated.getUserName()))_{
//            checkUserName(userToBeUpdated.getUserName());
//            userToBeUpdated.setUserName(user.getUserName());
//        }

        if(!ObjectUtils.isEmpty(userToBeUpdated.getCity())){
            userToBeUpdated.setCity(user.getCity());
        }


        if(!ObjectUtils.isEmpty(userToBeUpdated.getState())){
            userToBeUpdated.setState(user.getState());
        }

        if(!ObjectUtils.isEmpty(userToBeUpdated.getZipCode())){
            userToBeUpdated.setZipCode(user.getZipCode());
        }

        if(!ObjectUtils.isEmpty(userToBeUpdated.getIsInterestedInDairyAllergies())){
            userToBeUpdated.setIsInterestedInDairyAllergies(user.getIsInterestedInDairyAllergies());
        }

        if(!ObjectUtils.isEmpty(userToBeUpdated.getIsInterestedInPeanutAllergies())){
            userToBeUpdated.setIsInterestedInPeanutAllergies(user.getIsInterestedInPeanutAllergies());
        }

        if(!ObjectUtils.isEmpty(userToBeUpdated.getIsInterestedInEggAllergies())){
            userToBeUpdated.setIsInterestedInEggAllergies(user.getIsInterestedInEggAllergies());
        }

        this.userRepository.save(userToBeUpdated);

    }

    @DeleteMapping
    public void deleteUser(@RequestBody User user){
        validateUser(user);
        this.userRepository.delete(user);
    }




    public void validateUser(User user){

        Optional<User> userOptional = this.userRepository.findByUserName(user.getUserName());

        if(userOptional.isPresent()){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    public void checkUserName(String userName){
        if (ObjectUtils.isEmpty(userName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}
