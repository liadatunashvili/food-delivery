package services;

import java.math.BigDecimal;
import java.util.ArrayList;

import models.Food;
import models.customLambda;

public interface CartOperations {

    void addItem(Food food);

    void removeItem(Food food);

    public ArrayList<Food> getFilteredItems(customLambda.FoodChecker filter);

    public BigDecimal calculateDiscount(customLambda.FoodDiscount discount, double discountPercent);

    public void displayCart(customLambda.FoodFormatter formatter);

    ArrayList<Food> viewItemsList();

    BigDecimal calculateTotal();
}

