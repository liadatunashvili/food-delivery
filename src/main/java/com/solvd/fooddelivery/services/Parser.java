package com.solvd.fooddelivery.services;
import com.solvd.fooddelivery.models.Order;
public interface Parser {
    Order parse(String resource);
}
