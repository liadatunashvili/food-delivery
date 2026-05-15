package com.solvd.fooddelivery.services;

import com.solvd.fooddelivery.models.Food;
import com.solvd.fooddelivery.models.FoodChecker;
import com.solvd.fooddelivery.models.FoodDiscount;
import com.solvd.fooddelivery.models.FoodFormatter;

import java.math.BigDecimal;
import java.util.ArrayList;

public interface CartOperations {

    void addItem(Food food);

    void removeItem(Food food);

    ArrayList<Food> getFilteredItems(FoodChecker filter);

    BigDecimal calculateDiscount(FoodDiscount discount, double discountPercent);

    void displayCart(FoodFormatter formatter);

    ArrayList<Food> viewItemsList();

    BigDecimal calculateTotal();
}
