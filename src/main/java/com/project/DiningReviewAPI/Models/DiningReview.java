/*A dining review consists of the following info:

who submitted, represented by their unique display name (String)
the restaurant, represented by its Id (Long)
an optional peanut score, on a scale of 1-5
an optional egg score, on a scale of 1-5
an optional dairy score, on a scale of 1-5
an optional commentary*/


package com.project.DiningReviewAPI.Models;


import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
@Table(name = "DININGREVIEWS")
@Getter @Setter
public class DiningReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "UserName")
    private String userName;

    @Column(name = "RestaurantId")
    private Long restaurantId;

    @Column(name = "PeanutScore", nullable=true)
    private Integer peanutScore;

    @Column(name = "EggScore", nullable=true)
    private Integer eggScore;

    @Column(name = "DairyScore", nullable=true)
    private Integer dairyScore;

    @Column(name = "Commentary", nullable=true)
    private String commentary;


    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    private ReviewStatus status;
}
