package com.example.ittalentsdominosproject.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public final class UserReturnDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
