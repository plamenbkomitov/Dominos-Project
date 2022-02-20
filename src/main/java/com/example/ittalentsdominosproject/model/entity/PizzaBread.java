package com.example.ittalentsdominosproject.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "pizza_breads")
public class PizzaBread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bread_type")
    private String bread;

    @Column(name = "bread_price")
    private Double price;

}
