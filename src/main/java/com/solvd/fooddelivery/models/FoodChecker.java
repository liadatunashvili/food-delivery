package com.solvd.fooddelivery.models;

@FunctionalInterface
public interface FoodChecker {
    boolean check(Food food);
}

