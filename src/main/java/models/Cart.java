package models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import exceptions.EmptyCartException;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private static final Logger logger = LogManager.getLogger(Cart.class);


    private final Customer owner;

    private List<Food> cartItems = new ArrayList<>();

    public Cart(Customer owner) {
        this.owner = owner;
    }

    public Customer getOwner() {
        return owner;
    }

    public List<Food> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<Food> cartItems) {

        this.cartItems = cartItems;
    }


    public void clear() {
        this.cartItems = List.of(new Food[0]);
    }

    public void addToCart(Food food) {
        cartItems.add(food);
    }

    public void removeFromCart(Food food) {
        if (cartItems.isEmpty()) {
            throw new EmptyCartException();
        }
        if (!cartItems.remove(food)) {
            logger.info("could not find that item");
        }

        logger.info("cart is empty");
    }


}
