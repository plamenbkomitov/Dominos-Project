package com.example.ittalentsdominosproject.repository;

import com.example.ittalentsdominosproject.model.entity.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PizzaRepository extends JpaRepository<Pizza, Long> {
    Optional<Pizza> findPizzaByImage(String name);
}
