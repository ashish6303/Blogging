package com.example.blogging.controllers;

import com.example.blogging.entities.JwtRequest;
import com.example.blogging.entities.JwtResponse;
import com.example.blogging.entities.User;
import com.example.blogging.repository.UserRepo;
import com.example.blogging.security.JwtHelper;
import com.example.blogging.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {


    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtHelper helper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);


//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody JwtRequest request) {
//        System.out.println("hello");
//        User userDetails = userRepo.findByEmail(request.getEmail());
//        if(userDetails == null)
//        {
//            return new ResponseEntity<>("Email Doesn't Exists", HttpStatus.BAD_REQUEST);
//        } else {
//            this.doAuthenticate(request.getEmail(), request.getPassword());
//            String token = this.helper.generateToken(userDetails);
//            JwtResponse response = JwtResponse.builder()
//                    .jwtToken(token)
//                    .username(userDetails.getUsername()).build();
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }
//    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody JwtRequest request) {
//        User userDetails = userRepo.findByEmail(request.getEmail());
//        if (userDetails == null) {
//            throw new UsernameNotFoundException("Email Doesn't Exist");
//        }
//        // User exists, attempt authentication
//        try {
//            this.doAuthenticate(request.getEmail(), request.getPassword());
//            // If authentication succeeds, generate JWT token and return it
//            String token = this.helper.generateToken(userDetails);
//            JwtResponse response = JwtResponse.builder()
//                    .jwtToken(token)
//                    .username(userDetails.getUsername()).build();
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (BadCredentialsException e) {
//            return new ResponseEntity<>("Invalid Username or Password", HttpStatus.UNAUTHORIZED);
//        }
//    }

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody JwtRequest request) {
//        User userDetails = userRepo.findByEmail(request.getEmail());
//        if (userDetails == null) {
//            throw new UsernameNotFoundException("Email Doesn't Exist");
//        }
//
//        // User exists, attempt authentication
//        try {
//            this.doAuthenticate(request.getEmail(), request.getPassword());
//            // If authentication succeeds, generate JWT token and return it
//            String token = this.helper.generateToken(userDetails);
//            JwtResponse response = JwtResponse.builder()
//                    .jwtToken(token)
//                    .username(userDetails.getUsername()).build();
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } catch (BadCredentialsException e) {
//            return new ResponseEntity<>("Invalid Username or Password", HttpStatus.UNAUTHORIZED);
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest request) {
        User userDetails = userRepo.findByEmail(request.getEmail());
        if (userDetails == null) {
            return new ResponseEntity<>(String.format("%s Doesn't Exist", request.getEmail()), HttpStatus.NOT_FOUND);
        }

        // User exists, attempt authentication
        boolean isAuthenticated = doAuthenticate(request.getEmail(), request.getPassword());
        if (isAuthenticated) {
            // If authentication succeeds, generate JWT token and return it
            String token = this.helper.generateToken(userDetails);
            JwtResponse response = JwtResponse.builder()
                    .jwtToken(token)
                    .username(userDetails.getUsername()).build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid Username or Password", HttpStatus.UNAUTHORIZED);
        }
    }

    private boolean doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            Authentication user = manager.authenticate(authentication);
            System.out.println(user.getDetails());
            return true;
        } catch (BadCredentialsException e) {
            return false;
        }
    }

}
