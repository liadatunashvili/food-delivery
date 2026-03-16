package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Invoice extends FinantialRecord {

    private static int counter;

    private final int invoiceId;
    private final Customer customer;
    private final Payment payment;
    private final LocalDateTime issuedAt;

    public Invoice(Order order, Payment payment) {
        super(order, order.getTotal());
        this.invoiceId = counter++;
        this.customer = order.getCustomer();
        this.payment = payment;
        this.issuedAt = LocalDateTime.now();
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public Order getOrder() {
        return order;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Payment getPayment() {
        return payment;
    }

    public BigDecimal getTotalAmount() {
        return amount;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public boolean isPaid() {
        return payment != null && payment.isSuccess();
    }

    @Override
    public String describeRecord() {
        return "Invoice #" + invoiceId
                + " for order #" + order.getId()
                + " amount=" + amount;
    }

    public String generateSummary() {
        return "Invoice #" + invoiceId + "\n" +
                " | Order #" + order.getId() + "\n" +
                " | Customer: " + customer.getName() + "\n" +
                " | Total: " + amount + "\n" +
                " | Paid: " + isPaid() + "\n" +
                " | Issued at: " + issuedAt + "\n" +
                " | Signed by: FoodDelivery Company ";
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
        if (!(obj instanceof Invoice other)) {
            return false;
        }
        return invoiceId == other.invoiceId
                && Objects.equals(order, other.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceId, order);
    }
}

