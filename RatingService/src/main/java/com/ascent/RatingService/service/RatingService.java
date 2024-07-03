package com.ascent.RatingService.service;

import com.ascent.RatingService.entities.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {

    Rating create (Rating rating);

    List<Rating> getRating();

    List<Rating> getRatingByUserId(String userID);

    List<Rating> getRatingByHotelId(String hotelId);
}
