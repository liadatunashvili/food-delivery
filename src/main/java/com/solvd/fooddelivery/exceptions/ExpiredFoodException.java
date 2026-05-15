package com.solvd.fooddelivery.exceptions;

public class ExpiredFoodException extends RuntimeException {

    public ExpiredFoodException(String message) {
        super(message);
    }
}
