package com.example.ittalentsdominosproject.controller;

import com.example.ittalentsdominosproject.model.dto.PizzaDTO;
import com.example.ittalentsdominosproject.model.entity.Pizza;
import com.example.ittalentsdominosproject.repository.PizzaRepository;
import com.example.ittalentsdominosproject.service.PizzaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PizzaService pizzaService;

    @GetMapping("/pizza/{id}")
    public Pizza getByPizzaId(@PathVariable Long id) {
        return pizzaRepository.findById(id).orElseThrow();
    }

    @GetMapping("/pizza")
    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }
    @PostMapping("/pizza")
    public Pizza addPizza(@RequestBody PizzaDTO pizza) {
        Pizza p = new Pizza();
        p.setImage(pizza.getImage());
        p.setName(pizza.getName());
        p.setIngredients(pizzaService.add(pizza.getIngredientIds()));
        p.setPrice(pizzaService.calcPrice(p));
        return pizzaRepository.save(p);
    }

    @DeleteMapping("/pizza/{id}")
    public void deletePizzaById(@PathVariable Long id) {
        pizzaRepository.deleteById(id);
    }
}
