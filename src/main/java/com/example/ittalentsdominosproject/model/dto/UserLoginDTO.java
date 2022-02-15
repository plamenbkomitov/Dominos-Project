package com.example.ittalentsdominosproject.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Objects;
@Getter
@Setter
@NoArgsConstructor
public final class UserLoginDTO {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

}
