package com.example.ittalentsdominosproject.repository;

import com.example.ittalentsdominosproject.model.entity.PizzaOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaOrderRepository extends JpaRepository<PizzaOrder,Long> {
}
