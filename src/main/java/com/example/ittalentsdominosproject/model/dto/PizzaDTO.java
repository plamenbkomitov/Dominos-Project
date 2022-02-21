package com.example.ittalentsdominosproject.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class PizzaDTO {
    private String name;
    private Double price;
    private List<Long> ingredientIds;
}
