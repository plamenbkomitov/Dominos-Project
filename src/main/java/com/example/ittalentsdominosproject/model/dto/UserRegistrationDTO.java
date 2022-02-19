package com.example.ittalentsdominosproject.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String confPassword;
    private String phone;
}
