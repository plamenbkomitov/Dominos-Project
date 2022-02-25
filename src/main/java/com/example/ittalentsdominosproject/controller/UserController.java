package com.example.ittalentsdominosproject.controller;

import com.example.ittalentsdominosproject.model.dto.UserEditDTO;
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
    @Autowired
    private SessionHelper sessionHelper;

    @PostMapping("/login")
    public UserReturnDTO login(@RequestBody User u, HttpSession session) {
        String email = u.getEmail();
        String password = u.getPassword();
        User user = userService.login(email, password);
        Long userId = user.getId();
        sessionHelper.login(userId, session);
        return modelMapper.map(user, UserReturnDTO.class);
    }

    @PostMapping("/register")
    public UserReturnDTO register(@RequestBody UserRegistrationDTO u) {
        User user = userService.register(u);
        userRepository.save(user);
        return modelMapper.map(user, UserReturnDTO.class);
    }

    @PostMapping("/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }

    @DeleteMapping("/users")
    public void deleteUser(HttpSession session) {
        sessionHelper.isLogged(session);
        User user = sessionHelper.getUser(session);
        userRepository.deleteById(user.getId());
    }

    @PutMapping("/edit")
    public void editUser(@RequestBody UserEditDTO userEditDTO, HttpSession session) {
        sessionHelper.isLogged(session);
        User user = sessionHelper.getUser(session);
        userService.edit(user, userEditDTO);
        userRepository.save(user);
    }
}
