package com.example.ittalentsdominosproject.controller;

import com.example.ittalentsdominosproject.model.dto.UserLoginDTO;
import com.example.ittalentsdominosproject.model.dto.UserRegistrationDTO;
import com.example.ittalentsdominosproject.model.entity.User;
import com.example.ittalentsdominosproject.repository.UserRepository;
import com.example.ittalentsdominosproject.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class UserController {
    private final UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public UserLoginDTO login(@RequestBody User u, HttpSession session) {
        String email = u.getEmail();
        String password = u.getPassword();
        User user = userService.login(email,password);
        Long userId = user.getId();
        session.setAttribute("logged", userId);
        UserLoginDTO userLoginDTO = modelMapper.map(user,UserLoginDTO.class);
        return userLoginDTO;
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable long id) {
        User u = userRepository.findById(id).orElseThrow();
        return u;
    }

    @PostMapping("/register")
    public UserLoginDTO register(@RequestBody UserRegistrationDTO u) {
        User user = userService.register(u);
        userRepository.save(user);
        UserLoginDTO userLoginDTO = modelMapper.map(user,UserLoginDTO.class);
        return userLoginDTO;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable long id) {
        userRepository.deleteById(id);
    }
}
