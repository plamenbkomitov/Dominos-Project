package com.example.ittalentsdominosproject.model.dto;

import lombok.Data;

@Data
public class AddressWithUserDTO {
    private int id;
    private String addressName;
    private UserReturnDTO user;
}
