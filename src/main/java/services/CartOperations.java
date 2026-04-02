package services;

import models.Food;
import models.CustomLambda;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface CartOperations {

    void addItem(Food food);

    void removeItem(Food food);

    ArrayList<Food> getFilteredItems(CustomLambda.FoodChecker filter);

    BigDecimal calculateDiscount(CustomLambda.FoodDiscount discount, double discountPercent);

    void displayCart(CustomLambda.FoodFormatter formatter);

    ArrayList<Food> viewItemsList();

    BigDecimal calculateTotal();
}

