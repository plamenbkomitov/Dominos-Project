package com.example.ittalentsdominosproject.controller;

import com.example.ittalentsdominosproject.exception.NotFoundException;
import com.example.ittalentsdominosproject.exception.UnauthorizedException;
import com.example.ittalentsdominosproject.model.entity.Address;
import com.example.ittalentsdominosproject.model.entity.OtherProduct;
import com.example.ittalentsdominosproject.model.entity.User;
import com.example.ittalentsdominosproject.repository.AddressRepository;
import com.example.ittalentsdominosproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Optional;

@Component
public class SessionHelper {
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;

    public static final String LOGGED = "logged";
    public static final String OTHER_PRODUCT_CART = "other_product_cart";
    public static final String ADDRESS = "address";

    public void isLogged (HttpSession session) {
        if (session.isNew() || session.getAttribute(LOGGED) == null) {
            throw new UnauthorizedException("You're not logged in!");
        }
    }

    public void login(Long userId, HttpSession session) {
        session.setAttribute(LOGGED, userId);
        this.loadNewCart(session);
    }

    public User getUser(HttpSession session) {
        Long userId = (Long) session.getAttribute(LOGGED);
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty()) {
            throw new NotFoundException("No user exists!");
        }
        return user.get();
    }

    public Address getAddress(HttpSession session) {
        if(session.getAttribute(ADDRESS) == null) {
            throw new NotFoundException("You must choose an address for your order!");
        }


        int aId = (int) session.getAttribute(ADDRESS);

        Optional<Address> address = addressRepository.findById(aId);
        if(address.isEmpty()) {
            throw new NotFoundException("No address chosen!");
        }
        return address.get();
    }

    public void loadNewCart(HttpSession session) {
        session.setAttribute(OTHER_PRODUCT_CART, new HashMap<OtherProduct, Integer>());
    }

}
