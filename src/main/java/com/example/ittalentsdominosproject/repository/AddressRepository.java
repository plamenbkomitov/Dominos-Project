package com.example.ittalentsdominosproject.repository;

import com.example.ittalentsdominosproject.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> getAddressesByUser_Id(long id);

    Optional<Address> findById(int aId);
}
