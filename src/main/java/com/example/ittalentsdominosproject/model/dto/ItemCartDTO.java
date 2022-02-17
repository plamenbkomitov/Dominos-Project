package com.example.ittalentsdominosproject.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemCartDTO {
    private String name;
    private double price;
    private int amount;

    public ItemCartDTO(String name, double price, int amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }
}
