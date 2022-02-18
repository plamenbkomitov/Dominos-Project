package com.example.ittalentsdominosproject.service;

import com.example.ittalentsdominosproject.exception.NotFoundException;
import com.example.ittalentsdominosproject.model.entity.Ingredient;
import com.example.ittalentsdominosproject.model.entity.Pizza;
import com.example.ittalentsdominosproject.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {
    @Autowired
    private IngredientRepository ingredientRepository;
    public List<Ingredient> add(List<Long> ingredientIds) {
        List<Ingredient> ing=new ArrayList<>();
        for (int i = 0; i < ingredientIds.size(); i++) {
            Optional<Ingredient> in = ingredientRepository.findById(ingredientIds.get(i));
            if (!in.isPresent()){
                throw new NotFoundException("Ingredient not found");
            }
            Ingredient ingredient = in.get();
            ing.add(ingredient);
        }
        return ing;
    }

    public Double calcPrice(Pizza p) {
        double price=0;
        for (int i = 0; i < p.getIngredients().size(); i++) {
            price+=p.getIngredients().get(i).getPrice();
        }
        return price;
    }
}
