package com.example.ittalentsdominosproject.controller;

import com.example.ittalentsdominosproject.model.dto.UserReturnDTO;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/login")
    public UserReturnDTO login(@RequestBody User u, HttpSession session) {
        String email = u.getEmail();
        String password = u.getPassword();
        User user = userService.login(email,password);
        Long userId = user.getId();
        session.setAttribute("logged", userId);
        UserReturnDTO userReturnDTO = modelMapper.map(user, UserReturnDTO.class);
        return userReturnDTO;
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable long id) {
        User u = userRepository.findById(id).orElseThrow();
        return u;
    }

    @PostMapping("/register")
    public UserReturnDTO register(@RequestBody UserRegistrationDTO u) {
        User user = userService.register(u);
        userRepository.save(user);
        UserReturnDTO userReturnDTO = modelMapper.map(user, UserReturnDTO.class);
        return userReturnDTO;
    }
    @PostMapping("/logout")
    public void logout(HttpSession session){
        session.invalidate();
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable long id) {
        userRepository.deleteById(id);
    }

}
