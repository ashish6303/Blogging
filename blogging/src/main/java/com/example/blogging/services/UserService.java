package com.example.blogging.services;

import com.example.blogging.entities.User;
import com.example.blogging.exceptions.ResourseNotFoundException;
import com.example.blogging.payloads.UserDto;
import com.example.blogging.repository.UserRepo;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public ResponseEntity<?> createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        if(userRepo.findByEmail(user.getEmail())!=null){
            return new ResponseEntity<>("User Already Exists", HttpStatus.CONFLICT);
        }
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User savedUser = userRepo.save(user);
        return new ResponseEntity<>("User Created Succesfully",HttpStatus.OK);
    }

    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User", "Id", userId));

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(user.getPassword());
        user.setAbout(userDto.getAbout());

        User updateUser = userRepo.save(user);
        UserDto updateUserDto = userToDto(updateUser);
        return updateUserDto;

    }

    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User", "Id", userId));
        return userToDto(user);
    }


    public List<UserDto> getAllUser() {
        List<User> users = userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }


    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);
    }


    private User dtoToUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        return user;
    }

    private UserDto userToDto(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username);
    }


}
