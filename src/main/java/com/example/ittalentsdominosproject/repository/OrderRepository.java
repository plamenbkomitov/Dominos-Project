package com.example.ittalentsdominosproject.repository;

import com.example.ittalentsdominosproject.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
