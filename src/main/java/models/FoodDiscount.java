package models;

@FunctionalInterface
public interface FoodDiscount {
    double applyDiscount(Food food, double discountPercent);
}

