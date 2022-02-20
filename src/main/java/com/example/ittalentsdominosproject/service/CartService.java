package com.example.ittalentsdominosproject.service;

import com.example.ittalentsdominosproject.controller.SessionHelper;
import com.example.ittalentsdominosproject.exception.BadRequestException;
import com.example.ittalentsdominosproject.model.dto.ItemCartDTO;
import com.example.ittalentsdominosproject.model.dto.OrderInstructionsDTO;
import com.example.ittalentsdominosproject.model.dto.PizzaToCartDTO;
import com.example.ittalentsdominosproject.model.entity.*;
import com.example.ittalentsdominosproject.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
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
    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private PizzaBreadRepository pizzaBreadRepository;
    @Autowired
    private PizzaSizeRepository pizzaSizeRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private PizzaOrderRepository pizzaOrderRepository;
    @Autowired
    private SessionHelper sessionHelper;

    public List<ItemCartDTO> completeOrder(
            HashMap<OtherProduct, Integer> otherProductCart,
            Address address, User user, OrderInstructionsDTO orderInstructionsDTO,
            HashMap<PizzaToCartDTO, Integer> pizzaCart, HttpSession session) {
        if (otherProductCart.isEmpty() && pizzaCart.isEmpty()) {
            throw new BadRequestException("Cart is empty!");
        }

        Order order = modelMapper.map(orderInstructionsDTO, Order.class);
        order.setLocalDate(LocalDate.now());
        order.setUser(user);
        order.setAddress(address);
        orderRepository.save(order);

        List<ItemCartDTO> receipt = new ArrayList<>();

        getOtherProductValue(otherProductCart, order, receipt, true);
        getPizzaValue(pizzaCart, order, receipt, true);
        return this.getReceipt(session);
    }

    public List<ItemCartDTO> getReceipt(HttpSession session) {
        List<ItemCartDTO> receipt = new ArrayList<>();
        this.getPizzaValue(sessionHelper.getPizzasCart(session), null, receipt, false);
        this.getOtherProductValue(sessionHelper.getOtherProductsCart(session), null, receipt, false);
        return receipt;
    }

    private void getPizzaValue(HashMap<PizzaToCartDTO, Integer> pizzaCart, Order order, List<ItemCartDTO> receipt, boolean isOrder) {
        for (Map.Entry<PizzaToCartDTO, Integer> p : pizzaCart.entrySet()) {
            int quantity = p.getValue();
            Optional<Pizza> pizza = pizzaRepository.findById((long) p.getKey().getPizza_id());
            if (pizza.isEmpty()) {
                throw new BadRequestException("Required pizza not found");
            }
            double pizzaPrice = pizza.get().getPrice();
            Optional<PizzaBread> pizzaBread = pizzaBreadRepository.findById((long) p.getKey().getPizzaBread_id());
            if (pizzaBread.isEmpty()) {
                throw new BadRequestException("Required bread not found");
            }
            double pizzaBreadPrice = pizzaBread.get().getPrice();
            Optional<PizzaSize> pizzaSize = pizzaSizeRepository.findById((long) p.getKey().getPizzaSize_id());
            if (pizzaSize.isEmpty()) {
                throw new BadRequestException("Required size not found");
            }
            double pizzaSizePrice = pizzaSize.get().getPrice();
            double ingredientPrice = 0;
            List<Long> ingredients = p.getKey().getIngredients_ids();
            for (Long i : ingredients) {
                Optional<Ingredient> ingredient = ingredientRepository.findById(Long.valueOf(i));
                if (ingredient.isEmpty()) {
                    throw new BadRequestException("Required ingredient not found");
                }
                ingredientPrice += ingredient.get().getPrice();
            }
            double totalPrice = ingredientPrice + pizzaBreadPrice + pizzaSizePrice + pizzaPrice;
            if (isOrder) {
                pizzaOrderRepository.save(new PizzaOrder(order, pizza.get(), quantity, pizzaBread.get(), pizzaSize.get()));
            }
            receipt.add(new ItemCartDTO(pizza.get().getName(), totalPrice * quantity, quantity));
        }

    }

    private void getOtherProductValue(HashMap<OtherProduct, Integer> otherProductCart, Order order, List<ItemCartDTO> receipt, boolean isOrder) {
        for (Map.Entry<OtherProduct, Integer> o : otherProductCart.entrySet()) {
            int quantity = o.getValue();
            OtherProduct otherProduct = o.getKey();
            double price = otherProduct.getPrice() * quantity;
            OtherProductOrder otherProductOrder = new OtherProductOrder(order, otherProduct, quantity);

            if (isOrder) {
                otherProductOrderRepository.save(otherProductOrder);
            }

            receipt.add(new ItemCartDTO(otherProduct.getName(), price, quantity));
        }

    }

    public OtherProduct addOtherProductToCart(int itemId,
                                              HashMap<OtherProduct, Integer> otherProductCart) {
        Optional<OtherProduct> optionalOtherProduct =
                otherProductRepository.findById((long) itemId);
        if (optionalOtherProduct.isEmpty()) {
            throw new BadRequestException("Requested item not found!");
        }
        OtherProduct otherProduct = optionalOtherProduct.get();

        if (otherProductCart.containsKey(otherProduct)) {
            otherProductCart.put(otherProduct, otherProductCart.get(otherProduct) + 1);
        } else {
            otherProductCart.put(otherProduct, 1);
        }

        return otherProduct;
    }

    public OtherProduct reduceOtherProductInCart(int itemId, HashMap<OtherProduct, Integer> otherProductCart) {
        Optional<OtherProduct> otherProductOptional = otherProductRepository.findById((long) itemId);
        if (otherProductOptional.isEmpty()) {
            throw new BadRequestException("Requested item not found!");
        }

        OtherProduct otherProduct = otherProductOptional.get();

        if (otherProductCart.containsKey(otherProduct)) {
            if (otherProductCart.get(otherProduct) > 1) {
                otherProductCart.put(otherProduct, otherProductCart.get(otherProduct) - 1);
            } else {
                otherProductCart.remove(otherProduct);
            }
        } else {
            throw new BadRequestException("No such item in cart!");
        }

        return otherProduct;
    }

    public PizzaToCartDTO addPizzaToCart(PizzaToCartDTO pizza, HashMap<PizzaToCartDTO, Integer> pizzaCart) {
        Optional<Pizza> p = pizzaRepository.findById((long) pizza.getPizza_id());
        if (p.isEmpty()) {
            throw new BadRequestException("Required pizza not found");
        }

        if (pizzaCart.containsKey(pizza)) {
            pizzaCart.put(pizza, pizzaCart.get(pizza) + 1);
        } else {
            pizzaCart.put(pizza, 1);
        }
        return pizza;
    }

    public PizzaToCartDTO reducePizzaInCart(PizzaToCartDTO pizza, HashMap<PizzaToCartDTO, Integer> pizzaCart) {
        Optional<Pizza> p = pizzaRepository.findById((long) pizza.getPizza_id());
        if (p.isEmpty()) {
            throw new BadRequestException("Required pizza not found");
        }

        if (pizzaCart.containsKey(pizza)) {
            if (pizzaCart.get(pizza) > 1) {
                pizzaCart.put(pizza, pizzaCart.get(pizza) - 1);
            } else {
                pizzaCart.remove(pizza);
            }
        } else {
            throw new BadRequestException("No such item in cart!");
        }

        return pizza;
    }
}
