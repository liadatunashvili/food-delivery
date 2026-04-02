package services;

import models.Food;
import models.FoodChecker;
import models.FoodDiscount;
import models.FoodFormatter;

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
