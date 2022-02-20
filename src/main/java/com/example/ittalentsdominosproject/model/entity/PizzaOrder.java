package com.example.ittalentsdominosproject.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "orders_have_pizzas")
public class PizzaOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "pizza_id")
    private OtherProduct otherProduct;

    private int amount;

    @ManyToOne
    @JoinColumn(name = "pizza_breads_id")
    private OtherProduct pizzaBread;

    @ManyToOne
    @JoinColumn(name = "pizza_sizes_id")
    private OtherProduct pizzaSize;

}
