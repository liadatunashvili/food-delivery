package models;

public class EmptyCartException extends RuntimeException {

    public EmptyCartException() {
        super("cart is empty");
    }
}
