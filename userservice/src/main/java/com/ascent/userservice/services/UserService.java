package com.ascent.userservice.services;

import com.ascent.userservice.entity.User;
import java.util.List;

public interface UserService {
    public User saveUser(User user);
    List<User> getAllUser();
    public User getUser(String userId);
}
