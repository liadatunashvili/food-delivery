package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FinantialRecord {

    protected final Order order;
    protected final BigDecimal amount;
    protected final LocalDateTime createdAt;

    public FinantialRecord(Order order, BigDecimal amount) {
        this.order = order;
        this.amount = amount;
        this.createdAt = LocalDateTime.now();
    }

    public Order getOrder() {
        return order;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String describeRecord() {
        return "Order financial record #" + order.getId() + " amount=" + amount;
    }
}
