package com.example.ittalentsdominosproject.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "orders_have_other_products")
public class OtherProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "other_product_id")
    private OtherProduct otherProduct;

    private int amount;


    public OtherProductOrder(Order order, OtherProduct otherProduct, int amount) {
        this.order = order;
        this.otherProduct = otherProduct;
        this.amount = amount;
    }
}
