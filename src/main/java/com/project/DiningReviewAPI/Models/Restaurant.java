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
@Getter @Setter
@Table(name = "RESTAURANTS")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AvgPeanutScore")
    private String peanutScore;  // average score for peanut allergy safety - string as we want them to be in 2 decimal places as requested
    @Column(name = "AvgEggScore")
    private String eggScore;     // average score for egg allergy safety - string as we want them to be in 2 decimal places as requested
    @Column(name = "AvgDairyScore")
    private String dairyScore;   // average score for dairy allergy safety - string as we want them to be in 2 decimal places as requested


    @Column(name = "OverallScore")
    private String overallScore; // average across all categories - string as we want them to be in 2 decimal places as requested

    @Column(name = "ZipCode")
    private String zipCode;

    @Column(name = "line1")
    private String line1;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "website")
    private String website;


}
