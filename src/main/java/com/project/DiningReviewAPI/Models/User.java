/*A user consists of the following info:

their display name, one that’s unique to only that user
city
state
zipcode
whether they’re interested in peanut allergies
whether they’re interested in egg allergies
whether they’re interested in dairy allergies*/


package com.project.DiningReviewAPI.Models;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USERS")
@Getter @Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "UserName", unique = true)
    private String userName;

    @Column(name = "City")
    private String city;

    @Column(name = "State")
    private String state;

    @Column(name = "ZipCode")
    private String zipCode;

    @Column(name = "IsInterestedInPeanutAllergies")
    private Boolean isInterestedInPeanutAllergies;

    @Column(name = "IsInterestedInEggAllergies")
    private Boolean isInterestedInEggAllergies;

    @Column(name = "IsInterestedInDairyAllergies")
    private Boolean isInterestedInDairyAllergies;

}
