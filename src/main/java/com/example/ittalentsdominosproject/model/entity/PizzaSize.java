package com.example.ittalentsdominosproject.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pizza_sizes")
public class PizzaSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "size")
    private int size;

    @Column(name = "size_price")
    private double price;
}
