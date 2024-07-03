package com.ascent.userservice.external.service;

import com.ascent.userservice.entity.Ratings;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public interface RatingService {

    //GET


    //POST
    @PostMapping("/rating")
    public Ratings createRating(Ratings values);

    //PUT
    @PutMapping("/rating/{ratingId}")
    public Ratings updateRating(@PathVariable("ratingId") String ratingId, Ratings ratings);


}
