package com.example.ittalentsdominosproject.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserEditDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String newPassword;
    private String confNewPassword;
    private String phone;

}
