package com.ascent.userservice.services;

import com.ascent.userservice.Exceptions.ResourceNotFoundException;
import com.ascent.userservice.entity.Hotel;
import com.ascent.userservice.entity.Ratings;
import com.ascent.userservice.entity.User;
import com.ascent.userservice.external.service.HotelService;
import com.ascent.userservice.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        // generate unique id
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        // Get user from database with the help of user repository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server !! : " + userId));

        // http://localhost:8082/rating/users/38d07886-1b48-443b-a52f-daef3b416542

       Ratings[] ratingsOfUser = restTemplate.getForObject("http://RATINGSERVICE/rating/users/"+user.getUserId(), Ratings[].class);
        logger.info("{}", ratingsOfUser);

        List<Ratings> ratings = Arrays.stream(ratingsOfUser).toList();

        List<Ratings> ratingsList = ratings.stream().map(rating -> {

            //api call to hotel service to get the hotel
            //http://localhost:8081/hotels/38d07886-1b48-443b-a52f-daef3b416542

//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTELSERVICE/hotels/"+rating.getHotelId(), Hotel.class);
//            Hotel hotel = forEntity.getBody();
//            logger.info("response status code: {} ",forEntity.getStatusCode());

            Hotel hotel = hotelService.getHotel(rating.getHotelId());
            //set the hotel to rating
            rating.setHotel(hotel);

            //return the rating
            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingsList);
        return user;
    }
}
