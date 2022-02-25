package com.example.ittalentsdominosproject.controller;

import com.example.ittalentsdominosproject.model.dto.ItemCartDTO;
import com.example.ittalentsdominosproject.model.dto.OrderInstructionsDTO;
import com.example.ittalentsdominosproject.model.dto.PizzaToCartDTO;
import com.example.ittalentsdominosproject.model.entity.Address;
import com.example.ittalentsdominosproject.model.entity.OtherProduct;
import com.example.ittalentsdominosproject.model.entity.User;
import com.example.ittalentsdominosproject.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@RestController
public class CartController {
    @Autowired
    private SessionHelper sessionHelper;
    @Autowired
    private CartService cartService;

    @PostMapping("/cart/products/{itemId}")
    public OtherProduct addOtherProductToCart(@PathVariable int itemId,
                                              HttpSession session) {
        sessionHelper.isLogged(session);
        HashMap<OtherProduct, Integer> otherProductCart = sessionHelper.getOtherProductsCart(session);

        return cartService.addOtherProductToCart(itemId, otherProductCart);
    }


    @DeleteMapping("/cart/products/{itemId}")
    public OtherProduct ReduceOtherProductInCart(@PathVariable int itemId,
                                                 HttpSession session) {
        sessionHelper.isLogged(session);
        HashMap<OtherProduct, Integer> otherProductCart = sessionHelper.getOtherProductsCart(session);

        return cartService.reduceOtherProductInCart(itemId, otherProductCart);
    }

    @PostMapping("/cart/pizzas")
    public PizzaToCartDTO addPizzaToCart(@RequestBody PizzaToCartDTO pizza, HttpSession session) {
        sessionHelper.isLogged(session);
        HashMap<PizzaToCartDTO, Integer> pizzaCart = sessionHelper.getPizzasCart(session);
        return cartService.addPizzaToCart(pizza, pizzaCart);
    }

    @DeleteMapping("/cart/pizzas")
    public PizzaToCartDTO ReducePizzaInCart(@RequestBody PizzaToCartDTO pizza,
                                            HttpSession session) {
        sessionHelper.isLogged(session);
        HashMap<PizzaToCartDTO, Integer> pizzaCart = sessionHelper.getPizzasCart(session);

        return cartService.reducePizzaInCart(pizza, pizzaCart);
    }

    @DeleteMapping("/cart")
    public void clearCart(HttpSession session) {
        sessionHelper.isLogged(session);
        sessionHelper.loadNewCart(session);
    }

    @PostMapping("/pay")
    public List<ItemCartDTO> completeOrder(@RequestBody OrderInstructionsDTO orderInstructionsDTO, HttpSession session) {
        sessionHelper.isLogged(session);

        HashMap<OtherProduct, Integer> otherProductCart = sessionHelper.getOtherProductsCart(session);
        HashMap<PizzaToCartDTO, Integer> pizzaCart = sessionHelper.getPizzasCart(session);

        User user = sessionHelper.getUser(session);
        Address address = sessionHelper.getAddress(session);

        List<ItemCartDTO> receipt = cartService.completeOrder(otherProductCart, address, user, orderInstructionsDTO, pizzaCart);
        sessionHelper.loadNewCart(session);
        return receipt;
    }

    @GetMapping("/cart")
    public List<ItemCartDTO> getCart(HttpSession session) {
        sessionHelper.isLogged(session);
        return cartService.getReceipt(sessionHelper.getOtherProductsCart(session), sessionHelper.getPizzasCart(session), null);
    }
}
