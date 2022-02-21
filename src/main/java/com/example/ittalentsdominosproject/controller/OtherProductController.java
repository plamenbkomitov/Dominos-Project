package com.example.ittalentsdominosproject.controller;

import com.example.ittalentsdominosproject.exception.NotFoundException;
import com.example.ittalentsdominosproject.model.entity.OtherProduct;
import com.example.ittalentsdominosproject.repository.OtherProductRepository;
import com.example.ittalentsdominosproject.service.ImageService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;
import java.util.List;

@RestController
public class OtherProductController {
    @Autowired
    private OtherProductRepository otherProductRepository;
    @Autowired
    private ImageService imageService;

    @GetMapping("/product/{id}")
    public OtherProduct getOtherProductById(@PathVariable Long id) {
        return otherProductRepository.findById(id).orElseThrow();
    }
    @PostMapping("/product/image")
    public String uploadOtherProductImage(@RequestParam(name = "file") MultipartFile image,
                                   @RequestParam(name = "product_id") Long id){
        boolean isPizzaImage = false;
        return imageService.uploadImage(image,id,isPizzaImage);
    }

    @GetMapping("/product/image/{name}")
    public void downloadImage(@PathVariable String name, HttpServletResponse response){
        boolean isPizzaImage=false;
        imageService.downloadImage(name,response,isPizzaImage);
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
