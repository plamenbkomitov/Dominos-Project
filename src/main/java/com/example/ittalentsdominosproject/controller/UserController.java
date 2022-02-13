package com.example.ittalentsdominosproject.controller;

import com.example.ittalentsdominosproject.model.User;
import com.example.ittalentsdominosproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable long id) {
        User u = userRepository.findById(id).orElseThrow();
        return u;
    }

    @PostMapping("/users")
    public User register(@RequestBody User u) {
        userRepository.save(u);
        return u;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable long id) {
       userRepository.deleteById(id);
    }
}
