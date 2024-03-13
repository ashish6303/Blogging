package com.example.blogging.repository;

import com.example.blogging.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;


public interface UserRepo extends JpaRepository<User, Integer> {

}
