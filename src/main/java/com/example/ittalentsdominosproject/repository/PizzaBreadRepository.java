package com.example.ittalentsdominosproject.repository;

import com.example.ittalentsdominosproject.model.entity.PizzaBread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaBreadRepository extends JpaRepository<PizzaBread,Long> {
}
