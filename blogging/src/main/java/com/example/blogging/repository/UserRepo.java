package com.example.blogging.repository;

import com.example.blogging.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;


public interface UserRepo extends JpaRepository<User, Integer> {

    User findByEmail(String email);

}
