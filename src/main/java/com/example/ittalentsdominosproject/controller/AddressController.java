package com.example.ittalentsdominosproject.controller;

import com.example.ittalentsdominosproject.exception.UnauthorizedException;
import com.example.ittalentsdominosproject.model.dto.AddressRegistrationDTO;
import com.example.ittalentsdominosproject.model.dto.AddressReturnDTO;
import com.example.ittalentsdominosproject.model.dto.UserLoginDTO;
import com.example.ittalentsdominosproject.model.entity.Address;
import com.example.ittalentsdominosproject.model.entity.User;
import com.example.ittalentsdominosproject.repository.UserRepository;
import com.example.ittalentsdominosproject.service.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
public class AddressController {
    @Autowired
    private SessionHelper sessionHelper;
    @Autowired
    private AddressService addressService;
    @Autowired
    private UserRepository userRepository;


    @PutMapping("/addresses")
    public AddressReturnDTO addAddress(@RequestBody AddressRegistrationDTO addressRegistrationDTO, HttpSession session) {
        sessionHelper.isLogged(session);
        Long userId = (Long) session.getAttribute("logged");
        Optional<User> user = userRepository.findById(userId);
        return addressService.addAddress(addressRegistrationDTO, user.orElse(null));
    }
}
