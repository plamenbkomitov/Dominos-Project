package com.example.ittalentsdominosproject.controller;

import com.example.ittalentsdominosproject.exception.NotFoundException;
import com.example.ittalentsdominosproject.model.dto.IngredientDTO;
import com.example.ittalentsdominosproject.model.entity.Ingredient;
import com.example.ittalentsdominosproject.repository.IngredientRepository;
import com.example.ittalentsdominosproject.service.IngredientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
public class IngredientController {
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private SessionHelper sessionHelper;

    @PostMapping("/ingredients")
    public Ingredient addIngredient(@RequestBody IngredientDTO ingredient, HttpSession session) {
        sessionHelper.isLogged(session);
        sessionHelper.isAdmin(session);
        Ingredient i = modelMapper.map(ingredient, Ingredient.class);
        return ingredientRepository.save(i);
    }

    @PutMapping("/ingredients/update/{id}")
    public Ingredient editIngredient(@PathVariable long id, @RequestBody IngredientDTO ingredient, HttpSession session) {
        sessionHelper.isLogged(session);
        sessionHelper.isAdmin(session);
        Optional<Ingredient> i = ingredientRepository.findById(id);
        if (i.isEmpty()) {
            throw new NotFoundException("Ingredient not found");
        }
        Ingredient ing = ingredientService.updateIngredient(i.get(), ingredient);
        ingredientRepository.save(ing);
        return ing;
    }
}
