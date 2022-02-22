package com.example.ittalentsdominosproject.service;

import com.example.ittalentsdominosproject.exception.BadRequestException;
import com.example.ittalentsdominosproject.model.dto.AddressRegistrationDTO;
import com.example.ittalentsdominosproject.model.entity.Address;
import com.example.ittalentsdominosproject.model.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InfoValidator {

    public void passwordValidate(String password) {

        String passPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}";
        if (!password.matches(passPattern)) {
            throw new BadRequestException("Password not strong enough!");
        }
    }

    public void emailValidate(String email) {
        String emailPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        if (!email.matches(emailPattern)) {
            throw new BadRequestException("Enter a valid email!");
        }
    }

    public void firstNameValidate(String firstName) {
        if (firstName.trim().equals("")) {
            throw new BadRequestException("Name can not be empty!");
        }
    }

    public void lastNameValidate(String lastName) {
        if (lastName.trim().equals("")) {
            throw new BadRequestException("Name can not be empty!");
        }
    }

    public void phoneValidate(String phone) {
        String phonePattern = "\\d+";
        if (!phone.matches(phonePattern)) {
            throw new BadRequestException("Enter a valid phone number!");
        }
    }

    public void addressUniquenessValidate(AddressRegistrationDTO addressRegistrationDTO,
                                          User user) {

        List<Address> addresses = user.getAddresses();

        for (Address address : addresses) {
            if (address.getAddressName().trim().
                    equalsIgnoreCase(addressRegistrationDTO.getAddressName())) {
                throw new BadRequestException("Address must be unique!");
            }
        }
    }

    public void addressPresentValidate(String addressName) {
        if (addressName == null || addressName.trim().equals("")) {
            throw new BadRequestException("You must input an address!");
        }
    }
}
