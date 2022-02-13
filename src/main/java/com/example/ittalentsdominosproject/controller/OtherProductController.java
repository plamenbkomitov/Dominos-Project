package com.example.ittalentsdominosproject.controller;

import com.example.ittalentsdominosproject.model.OtherProduct;
import com.example.ittalentsdominosproject.repository.OtherProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OtherProductController {
    private final OtherProductRepository otherProductRepository;

    public OtherProductController(OtherProductRepository otherProductRepository) {
        this.otherProductRepository = otherProductRepository;
    }

    @GetMapping("/product/{id}")
    public OtherProduct getOtherProductById(@PathVariable Long id) {
        return otherProductRepository.findById(id).orElseThrow();
    }

    @GetMapping("/product")
    public List<OtherProduct> getAllOtherProducts(){
        return otherProductRepository.findAll();
    }

    @PostMapping("/product")
    public void addOtherProduct(@RequestBody OtherProduct otherProduct) {
        otherProductRepository.save(otherProduct);
    }

    @DeleteMapping("/product/{id}")
    public void deleteOtherProductById(@PathVariable Long id) {
        otherProductRepository.deleteById(id);
    }

}
