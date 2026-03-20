package services;

import java.math.BigDecimal;

import models.Cart;
import models.Food;

public class CartService implements CartOperations {

    public static final String SERVICE_NAME = "CartService";
    private final Cart cart;

    public CartService(Cart cart) {
        this.cart = cart;
    }

    public void addItem(Food food) {
        cart.addToCart(food);
    }

    public void removeItem(Food food) {
        cart.removeFromCart(food);
    }

    public Food[] viewItems() {
        return cart.getCartItems();
    }

    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Food item : cart.getCartItems()) {
            total = total.add(item.getFoodPrice());
        }
        return total;
    }
}