//package com.example.blogging.services.ServiceImpl;
//
//import com.example.blogging.entities.User;
//import com.example.blogging.exceptions.ResourseNotFoundException;
//import com.example.blogging.payloads.UserDto;
//import com.example.blogging.repository.UserRepo;
//import com.example.blogging.services.UserService;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class UserServiceImpl implements UserService {
//
//    @Autowired
//    private UserRepo userRepo;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    @Override
//    public UserDto createUser(UserDto userDto) {
//        User user = this.dtoToUser(userDto);
//        User  savedUser = userRepo.save(user);
//        return userToDto(savedUser);
//    }
//
//    @Override
//    public UserDto updateUser(UserDto userDto, Integer userId) {
//        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User","Id",userId));
//
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(user.getPassword());
//        user.setAbout(userDto.getAbout());
//
//        User updateUser = userRepo.save(user);
//        UserDto updateUserDto = userToDto(updateUser);
//        return updateUserDto;
//
//    }
//
//    @Override
//    public UserDto getUserById(Integer userId) {
//        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User","Id",userId));
//        return userToDto(user);
//    }
//
//    @Override
//    public List<UserDto> getAllUser() {
//        List<User> users = userRepo.findAll();
//        List<UserDto> userDtos = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
//        return userDtos;
//    }
//
//    @Override
//    public void deleteUser(Integer userId) {
//        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourseNotFoundException("User","Id",userId));
//        this.userRepo.delete(user);
//    }
//
//
//    private User dtoToUser(UserDto userDto)
//    {
//        User user = modelMapper.map(userDto, User.class);
////        User user = new User();
////        user.setId(userDto.getId());
////        user.setName(userDto.getName());
////        user.setEmail(userDto.getEmail());
////        user.setPassword(userDto.getPassword());
////        user.setAbout(userDto.getAbout());
//        return user;
//    }
//
//    private UserDto userToDto(User user)
//    {
//        UserDto userDto = modelMapper.map(user, UserDto.class);
//        return userDto;
//    }
//
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepo.findByEmail(username);
//    }
//}
