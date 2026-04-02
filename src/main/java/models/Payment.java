package models;

import enums.PaymentType;
import java.math.BigDecimal;
import java.util.Objects;

public class Payment extends FinantialRecord {

    private final Method method;
    private final PaymentType paymentType;
    private boolean success;

    public Payment(Order order, BigDecimal amount, Method method) {
        super(order, amount);
        this.method = method;
        this.paymentType = (method == Method.CARD) ? PaymentType.CARD : PaymentType.CASH;
        this.success = false;
    }

    public Method getMethod() {
        return method;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    @Override
    public String describeRecord() {
        return "Payment for order #" + order.getId()
                + " amount=" + amount
                + " method=" + method
                + " success=" + success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void markSuccess() {
        this.success = true;
    }

    @Override
    public String toString() {
        return describeRecord();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Payment other)) {
            return false;
        }
        return success == other.success
                && method == other.method
                && Objects.equals(order, other.order)
                && Objects.equals(amount, other.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, amount, method, success);
    }

    public enum Method {
        CASH,
        CARD
    }
}
