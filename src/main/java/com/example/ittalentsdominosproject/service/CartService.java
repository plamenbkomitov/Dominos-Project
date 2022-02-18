package com.example.ittalentsdominosproject.service;

import com.example.ittalentsdominosproject.exception.BadRequestException;
import com.example.ittalentsdominosproject.model.dto.ItemCartDTO;
import com.example.ittalentsdominosproject.model.dto.OrderInstructionsDTO;
import com.example.ittalentsdominosproject.model.entity.*;
import com.example.ittalentsdominosproject.repository.OrderRepository;
import com.example.ittalentsdominosproject.repository.OtherProductOrderRepository;
import com.example.ittalentsdominosproject.repository.OtherProductRepository;
import com.fasterxml.jackson.core.JsonToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
public class CartService {
    @Autowired
    OtherProductRepository otherProductRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OtherProductOrderRepository otherProductOrderRepository;
    @Autowired
    private ModelMapper modelMapper;


    public OtherProduct addOtherProductToCart(int itemId,
                                              HashMap<OtherProduct, Integer> otherProductCart) {
            Optional<OtherProduct> optionalOtherProduct =
                    otherProductRepository.findById((long) itemId);
            if(optionalOtherProduct.isEmpty()) {
                throw new BadRequestException("Requested item not found!");
            }
            OtherProduct otherProduct = optionalOtherProduct.get();

            if(otherProductCart.containsKey(otherProduct)) {
                otherProductCart.put(otherProduct, otherProductCart.get(otherProduct) + 1);
            } else {
                otherProductCart.put(otherProduct, 1);
            }

            return otherProduct;
    }

    public List<ItemCartDTO> completeOrder(
            HashMap<OtherProduct, Integer> otherProductCart,
            Address address, User user, OrderInstructionsDTO orderInstructionsDTO) {
        if(otherProductCart.isEmpty()) {
            throw new BadRequestException("Cart is empty!");
        }

        Order order = modelMapper.map(orderInstructionsDTO, Order.class);
        order.setLocalDate(LocalDate.now());
        order.setUser(user);
        order.setAddress(address);
        orderRepository.save(order);

        List<ItemCartDTO> receipt = new ArrayList<>();

        for (Map.Entry<OtherProduct, Integer> o : otherProductCart.entrySet()) {
            int quantity = o.getValue();
            OtherProduct otherProduct = o.getKey();
            double price = otherProduct.getPrice() * quantity;
            OtherProductOrder otherProductOrder = new OtherProductOrder(order, otherProduct, quantity);
            otherProductOrderRepository.save(otherProductOrder);

            receipt.add(new ItemCartDTO(otherProduct.getName(), price, quantity));
        }
        return receipt;
    }
}
