package com.example.ittalentsdominosproject.model.dto;

import com.example.ittalentsdominosproject.model.entity.Ingredient;
import com.example.ittalentsdominosproject.model.entity.Pizza;
import com.example.ittalentsdominosproject.model.entity.PizzaBread;
import com.example.ittalentsdominosproject.model.entity.PizzaSize;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
@EqualsAndHashCode
@Data
public class PizzaToCartDTO {
    private int pizza_id;
    private int pizzaSize_id;
    private int pizzaBread_id;
    private List<Long> ingredients_ids;
}
