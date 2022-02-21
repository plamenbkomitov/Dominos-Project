package com.example.ittalentsdominosproject.controller;

import com.example.ittalentsdominosproject.model.entity.OtherProduct;
import com.example.ittalentsdominosproject.repository.OtherProductRepository;
import com.example.ittalentsdominosproject.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
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
        return imageService.uploadImage(image,id, false);
    }

    @GetMapping("/product/image/{name}")
    public void downloadImage(@PathVariable String name, HttpServletResponse response){
        imageService.downloadImage(name,response, false);
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
