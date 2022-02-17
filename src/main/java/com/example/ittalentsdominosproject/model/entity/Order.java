package com.example.ittalentsdominosproject.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "ordered_at")
    private LocalDate localDate;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "instructions")
    private String instructions;
}
