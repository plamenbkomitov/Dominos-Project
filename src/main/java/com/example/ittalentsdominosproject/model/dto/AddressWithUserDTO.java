package com.example.ittalentsdominosproject.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressWithUserDTO {
    private int id;
    private String addressName;
    private UserReturnDTO user;
}
