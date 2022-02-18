package com.example.ittalentsdominosproject.model.dto;

import lombok.Data;

import javax.persistence.Column;
import java.util.List;

@Data
public class PizzaDTO {
    private String name;
    private Double price;
    private String image;
    private List<Long> ingredientIds;
}
