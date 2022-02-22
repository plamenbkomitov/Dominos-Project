package com.example.ittalentsdominosproject.controller;

import com.example.ittalentsdominosproject.model.dto.PizzaDTO;
import com.example.ittalentsdominosproject.model.entity.Pizza;
import com.example.ittalentsdominosproject.repository.PizzaRepository;
import com.example.ittalentsdominosproject.service.ImageService;
import com.example.ittalentsdominosproject.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private PizzaService pizzaService;
    @Autowired
    private SessionHelper sessionHelper;

    @GetMapping("/pizza/{id}")
    public Pizza getByPizzaId(@PathVariable Long id) {
        return pizzaRepository.findById(id).orElseThrow();
    }

    @GetMapping("/pizza")
    public List<Pizza> getAllPizzas() {
        return pizzaRepository.findAll();
    }
    @PostMapping("/pizza")
    public Pizza addPizza(@RequestBody PizzaDTO pizza, HttpSession session) {
        sessionHelper.isLogged(session);
        sessionHelper.isAdmin(session);
        Pizza p = new Pizza();
        p.setName(pizza.getName());
        p.setIngredients(pizzaService.add(pizza.getIngredientIds()));
        p.setPrice(pizzaService.calcPrice(p));
        return pizzaRepository.save(p);
    }
    @PostMapping("/pizza/image")
    public String uploadPizzaImage(@RequestParam(name = "file")MultipartFile image,
                                   @RequestParam(name = "pizza_id") Long id, HttpSession session) {
        sessionHelper.isLogged(session);
        sessionHelper.isAdmin(session);
        return imageService.uploadImage(image,id, true);
    }
    @GetMapping("/pizza/image/{name}")
    public void downloadImage(@PathVariable String name, HttpServletResponse response){
        imageService.downloadImage(name,response, true);
    }

    @DeleteMapping("/pizza/{id}")
    public void deletePizzaById(@PathVariable Long id, HttpSession session) {
        sessionHelper.isLogged(session);
        sessionHelper.isAdmin(session);
        pizzaRepository.deleteById(id);
    }
}
