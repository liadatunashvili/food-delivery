package services;

import models.Food;
import models.customLambda;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface CartOperations {

    void addItem(Food food);

    void removeItem(Food food);

    ArrayList<Food> getFilteredItems(customLambda.FoodChecker filter);

    BigDecimal calculateDiscount(customLambda.FoodDiscount discount, double discountPercent);

    void displayCart(customLambda.FoodFormatter formatter);

    ArrayList<Food> viewItemsList();

    BigDecimal calculateTotal();
}

