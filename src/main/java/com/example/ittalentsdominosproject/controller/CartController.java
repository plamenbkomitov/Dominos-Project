package com.example.ittalentsdominosproject.controller;

import com.example.ittalentsdominosproject.model.dto.ItemCartDTO;
import com.example.ittalentsdominosproject.model.dto.OrderInstructionsDTO;
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


    @PostMapping("/items/{itemId}")
    public OtherProduct addOtherProductToCart(@PathVariable int itemId,
                                              HttpSession session) {
        sessionHelper.isLogged(session);
        HashMap<OtherProduct, Integer> otherProductCart =
                (HashMap<OtherProduct, Integer>) session.getAttribute(SessionHelper.OTHER_PRODUCT_CART);
        return cartService.addOtherProductToCart(itemId, otherProductCart);
    }

    @PostMapping("/pay")
    public List<ItemCartDTO> completeOrder(@RequestBody OrderInstructionsDTO orderInstructionsDTO, HttpSession session) {
        sessionHelper.isLogged(session);

        HashMap<OtherProduct, Integer> otherProductCart =
                (HashMap<OtherProduct, Integer>) session.getAttribute(SessionHelper.OTHER_PRODUCT_CART);

        //TODO add pizza hashmap and send to cartService
        User user = sessionHelper.getUser(session);
        Address address = sessionHelper.getAddress(session);

        List<ItemCartDTO> receipt = cartService.completeOrder(otherProductCart, address, user, orderInstructionsDTO);
        sessionHelper.loadNewCart(session);
       return receipt;
    }
}
