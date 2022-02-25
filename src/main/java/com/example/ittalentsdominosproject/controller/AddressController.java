package com.example.ittalentsdominosproject.controller;

import com.example.ittalentsdominosproject.model.dto.AddressRegistrationDTO;
import com.example.ittalentsdominosproject.model.dto.AddressReturnDTO;
import com.example.ittalentsdominosproject.model.dto.AddressWithUserDTO;
import com.example.ittalentsdominosproject.model.entity.User;
import com.example.ittalentsdominosproject.repository.UserRepository;
import com.example.ittalentsdominosproject.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@RestController
public class AddressController {
    @Autowired
    private SessionHelper sessionHelper;
    @Autowired
    private AddressService addressService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/addresses")
    public AddressReturnDTO addAddress(@RequestBody AddressRegistrationDTO addressRegistrationDTO, HttpSession session) {
        sessionHelper.isLogged(session);
        User user = sessionHelper.getUser(session);
        return addressService.addAddress(addressRegistrationDTO, user);
    }

    @GetMapping("/addresses")
    public List<AddressWithUserDTO> getAllAddressesByLoggedUser(HttpSession session) {
        sessionHelper.isLogged(session);
        Long userId = (Long) session.getAttribute("logged");
        Optional<User> user = userRepository.findById(userId);
        return addressService.getAllAddresses(user);
    }

    @PutMapping("/addresses/{aId}")
    public AddressReturnDTO chooseAddress(@PathVariable long aId, HttpSession session) {
        sessionHelper.isLogged(session);
        User user = sessionHelper.getUser(session);
        AddressReturnDTO addressReturnDTO = addressService.chooseAddress(user, (int) aId);
        session.setAttribute(SessionHelper.ADDRESS, (int) aId);
        return addressReturnDTO;
    }

    @DeleteMapping("/addresses/{aId}")
    public AddressReturnDTO removeAddress(@PathVariable long aId, HttpSession session) {
        sessionHelper.isLogged(session);
        User user = sessionHelper.getUser(session);
        return addressService.removeAddress(user, (int) aId);
    }

    @PostMapping("addresses/{aId}")
    public AddressReturnDTO editAddress(
            @PathVariable long aId,
            @RequestBody AddressRegistrationDTO addressRegistrationDTO,
            HttpSession session) {
        sessionHelper.isLogged(session);
        User user = sessionHelper.getUser(session);
        return addressService.editAddress(user, (int) aId, addressRegistrationDTO);
    }
}
