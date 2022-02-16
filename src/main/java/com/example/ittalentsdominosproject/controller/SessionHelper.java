package com.example.ittalentsdominosproject.controller;

import com.example.ittalentsdominosproject.exception.UnauthorizedException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@Component
public class SessionHelper {

    public void  isLogged (HttpSession session) {
        if (session.isNew() || session.getAttribute("logged") == null) {
            throw new UnauthorizedException("You're not logged in!");
        }
    }

}
