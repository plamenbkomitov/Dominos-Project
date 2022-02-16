package com.example.ittalentsdominosproject.repository;

import com.example.ittalentsdominosproject.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
