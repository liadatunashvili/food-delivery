package models;

import java.math.BigDecimal;

public class Payment {
    public enum Method {
        CASH,
        CARD
    }

    private final BigDecimal amount;
    private final Method method;
    private boolean success;

    public Payment(BigDecimal amount, Method method) {
        this.amount = amount;
        this.method = method;
        this.success = false;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Method getMethod() {
        return method;
    }

    public boolean isSuccess() {
        return success;
    }

    public void markSuccess() {
        this.success = true;
    }
}
