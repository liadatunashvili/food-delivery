package services;

import java.math.BigDecimal;

import models.Food;

public interface CartOperations {
    void addItem(Food food);
    void removeItem(Food food);
    Food[] viewItems();
    BigDecimal calculateTotal();
}

