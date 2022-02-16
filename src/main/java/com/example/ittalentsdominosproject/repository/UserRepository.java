package com.example.ittalentsdominosproject.repository;

import com.example.ittalentsdominosproject.model.entity.Address;
import com.example.ittalentsdominosproject.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
}
