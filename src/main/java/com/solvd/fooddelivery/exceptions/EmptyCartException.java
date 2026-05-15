package com.solvd.fooddelivery.exceptions;

public class EmptyCartException extends RuntimeException {

    public EmptyCartException() {
        super("cart is empty");
    }
}
