package com.example.ittalentsdominosproject.controller;

import com.example.ittalentsdominosproject.model.entity.Pizza;
import com.example.ittalentsdominosproject.repository.PizzaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PizzaController {
    PizzaRepository pizzaRepository;

    public PizzaController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @GetMapping("pizza/{id}")
    public Pizza getByPizzaId(@PathVariable Long id) {
        return pizzaRepository.findById(id).orElseThrow();
    }

    @GetMapping("pizza/")
    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }

    @PostMapping("pizza/")
    public Pizza addPizza(@RequestBody Pizza pizza) {
        return pizzaRepository.save(pizza);
        //TODO add admin functionality?
    }

    @DeleteMapping("pizza/{id}")
    public void deletePizzaById(@PathVariable Long id) {
        pizzaRepository.deleteById(id);
    }
}
