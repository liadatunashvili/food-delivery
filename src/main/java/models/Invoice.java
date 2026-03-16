package models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Invoice {

    private static int counter;

    private final int invoiceId;
    private final Order order;
    private final Customer customer;
    private final Payment payment;
    private final BigDecimal totalAmount;
    private final LocalDateTime issuedAt;

    public Invoice(Order order, Payment payment) {
        this.invoiceId = counter++;
        this.order = order;
        this.customer = order.getCustomer();
        this.payment = payment;
        this.totalAmount = order.getTotal();
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
        return totalAmount;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public boolean isPaid() {
        return payment != null && payment.isSuccess();
    }

    public String generateSummary() {
        return "Invoice #" + invoiceId + "\n" +
                " | Order #" + order.getId() + "\n" +
                " | Customer: " + customer.getName() + "\n" +
                " | Total: " + totalAmount + "\n" +
                " | Paid: " + isPaid() + "\n" +
                " | Issued at: " + issuedAt + "\n" +
                " | Signed by: FoodDelivery Company ";
    }
}

