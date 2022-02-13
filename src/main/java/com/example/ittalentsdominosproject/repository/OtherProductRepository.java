package com.example.ittalentsdominosproject.repository;

import com.example.ittalentsdominosproject.model.OtherProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherProductRepository extends JpaRepository<OtherProduct, Long> {
}