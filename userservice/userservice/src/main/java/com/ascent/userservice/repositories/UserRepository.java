package com.ascent.userservice.repositories;

import com.ascent.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String > {

}
