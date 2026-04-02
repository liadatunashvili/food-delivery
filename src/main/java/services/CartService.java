package services;

import models.Cart;
import models.Food;
import models.FoodChecker;
import models.FoodDiscount;
import models.FoodFormatter;

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
        ArrayList<Food> filteredItems = new ArrayList<>();
        for (Food food : viewItemsList()) {
            if (filter.check(food)) {
                filteredItems.add(food);
            }
        }
        return filteredItems;
    }

    public BigDecimal calculateDiscount(FoodDiscount discount, double discountPercent) {
        BigDecimal total = BigDecimal.ZERO;
        for (Food foodItem : viewItemsList()) {
            double discounted = discount.applyDiscount(foodItem, discountPercent);
            total = total.add(BigDecimal.valueOf(discounted));
        }
        return total;
    }

    public void displayCart(FoodFormatter formatter) {
        for (Food food : viewItemsList()) {
            formatter.format(food);
        }
    }

    @Override
    public ArrayList<Food> viewItemsList() {
        return (ArrayList<Food>) cart.getCartItems();
    }


    public BigDecimal calculateTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (Food item : cart.getCartItems()) {
            total = total.add(item.getFoodPrice());
        }
        return total;
    }
}