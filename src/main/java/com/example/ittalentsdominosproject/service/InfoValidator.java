package com.example.ittalentsdominosproject.service;

import com.example.ittalentsdominosproject.exception.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class InfoValidator {

    public void passwordValidate(String password) {

        String passPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}";
        if(!password.matches(passPattern)) {
            throw new BadRequestException("Password not strong enough!");
        }
    }

    public void emailValidate(String email) {
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if(!email.matches(emailPattern)) {
            throw new BadRequestException("Enter a valid email!");
        }
    }

    public void firstNameValidate(String firstName) {
        if (firstName.trim().equals("")) {
            throw new BadRequestException("Name can not be empty!");
        }
    }

    public void lastNameValidate(String lastName) {
        if(lastName.trim().equals("")) {
            throw new BadRequestException("Name can not be empty!");
        }
    }

    public void phoneValidate(String phone) {
        String phonePattern = "\\d+";
        if(!phone.matches(phonePattern)) {
            throw new BadRequestException("Enter a valid phone number!");
        }
    }
}
