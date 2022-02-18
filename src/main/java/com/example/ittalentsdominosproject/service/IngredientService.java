package com.example.ittalentsdominosproject.service;

import com.example.ittalentsdominosproject.model.dto.IngredientDTO;
import com.example.ittalentsdominosproject.model.entity.Ingredient;
import org.springframework.stereotype.Service;

@Service
public class IngredientService {

    public Ingredient updateIngredient(Ingredient oldIngredient, IngredientDTO newIngredient) {
        oldIngredient.setName(newIngredient.getName());
        oldIngredient.setPrice(newIngredient.getPrice());

        return oldIngredient;
    }
}
