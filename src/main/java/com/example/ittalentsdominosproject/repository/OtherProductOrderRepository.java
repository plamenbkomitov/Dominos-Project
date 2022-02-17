package com.example.ittalentsdominosproject.repository;

import com.example.ittalentsdominosproject.model.entity.OtherProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherProductOrderRepository extends JpaRepository<OtherProductOrder, Long> {
}
