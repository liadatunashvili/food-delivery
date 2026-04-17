package services;

import models.*;

import java.math.BigDecimal;
import java.util.ArrayList;

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

    public ArrayList<Food> getFilteredItems(FoodChecker filter) {
        return viewItemsList().stream()
                .filter(filter::check)
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));
    }

    public BigDecimal calculateDiscount(FoodDiscount discount, double discountPercent) {
        return viewItemsList().stream()
                .map(foodItem -> discount.applyDiscount(foodItem, discountPercent))
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void displayCart(FoodFormatter formatter) {
        viewItemsList()
                .forEach(formatter::format);
    }

    @Override
    public ArrayList<Food> viewItemsList() {
        return new ArrayList<>(cart.getCartItems());
    }


    public BigDecimal calculateTotal() {
        return cart.getCartItems().stream()
                .map(Food::getFoodPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}