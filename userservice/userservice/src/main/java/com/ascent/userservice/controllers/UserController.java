package com.ascent.userservice.controllers;

import com.ascent.userservice.entity.User;
import com.ascent.userservice.services.UserService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    int retryCount = 1;
    @GetMapping("/{userId}")
    // @CircuitBreaker(name = "ratingHotelBeaker", fallbackMethod = "ratingHotelFallback") using circuit breaker
    // @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId) {
       logger.info("Get Single User Handler: UserController");

         // using Retry method
        //  logger.info("Retry count: {}", retryCount);
        //  retryCount ++;

        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    // Creating a fallBack method for circuit Breaker
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
       // logger.info("Fallback is executed because service is down", ex.getMessage());
      User user =  User.builder()
              .email("dummy@gmail.com")
              .name("Dummy")
              .about("This user is created dummy because it is down")
              .userId("123456")
              .build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> allUsers = userService.getAllUser();
        return ResponseEntity.ok(allUsers);
    }
}
