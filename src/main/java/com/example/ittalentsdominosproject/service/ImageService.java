package com.example.ittalentsdominosproject.service;

import com.example.ittalentsdominosproject.exception.BadRequestException;
import com.example.ittalentsdominosproject.exception.NotFoundException;
import com.example.ittalentsdominosproject.model.entity.OtherProduct;
import com.example.ittalentsdominosproject.model.entity.Pizza;
import com.example.ittalentsdominosproject.repository.OtherProductRepository;
import com.example.ittalentsdominosproject.repository.PizzaRepository;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;
import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private OtherProductRepository otherProductRepository;

    @SneakyThrows
    public void downloadImage(String name, HttpServletResponse response, boolean isPizzaImage) {
        File file = new File("uploads" + File.separator + name);
        if (!file.exists()) {
            throw new NotFoundException("Image not found");
        }
        if (isPizzaImage) {
            Optional<Pizza> pizza = pizzaRepository.findPizzaByImage(name);
            if (pizza.isEmpty()) {
                throw new BadRequestException("Image not found");
            }
        }
        else {
            Optional<OtherProduct> otherProduct= otherProductRepository.findOtherProductByImage(name);
            if (otherProduct.isEmpty()) {
                throw new BadRequestException("Image not found");
            }
        }
        Files.copy(file.toPath(), response.getOutputStream());

    }


    @SneakyThrows
    public String uploadImage(MultipartFile image, Long id, Boolean isPizzaImage) {
        String extension = FilenameUtils.getExtension(image.getOriginalFilename());
        String name = System.nanoTime() + "." + extension;


        if(extension == null) {
            throw new BadRequestException("File type extension is missing");
        }

        if(!(extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg") || extension.equalsIgnoreCase("png"))) {
            throw new BadRequestException("File type not allowed");
        }

        Files.copy(image.getInputStream(), new File("uploads" + File.separator + name).toPath());
        if (isPizzaImage) {
            Optional<Pizza> pizza = pizzaRepository.findById(id);
            if (pizza.isEmpty()) {
                throw new BadRequestException("No such pizza found");
            }
            pizza.get().setImage(name);
            pizzaRepository.save(pizza.get());
        } else {
            Optional<OtherProduct> otherProduct = otherProductRepository.findById(id);
            if (otherProduct.isEmpty()) {
                throw new BadRequestException("No such product found");
            }
            otherProduct.get().setImage(name);
            otherProductRepository.save(otherProduct.get());
        }
        return name;
    }
}
