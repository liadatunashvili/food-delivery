package services;

import java.math.BigDecimal;
import java.util.ArrayList;

import models.Food;

public interface CartOperations {

    void addItem(Food food);

    void removeItem(Food food);


    ArrayList<Food> viewItemsList();

    BigDecimal calculateTotal();
}

