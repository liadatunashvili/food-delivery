package models;

import java.math.BigDecimal;
import java.util.Arrays;

public class Order {

    public enum Status {
        CREATED, PAID, CANCELLED
    }

    private static int counter;

    private final int id;
    private final Customer customer;
    private final Food[] items;
    private final BigDecimal total;
    private Payment payment;
    private Invoice invoice;
    private OrderPlaces deliveryPlace;
    private Status status;

    public Order(Customer customer, Food[] items, BigDecimal total) {
        this.id = ++counter;
        this.customer = customer;
        this.items = Arrays.copyOf(items, items.length);
        this.total = total;
        this.status = Status.CREATED;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Food[] getItems() {
        return Arrays.copyOf(items, items.length);
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Payment getPayment() {
        return payment;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public OrderPlaces getDeliveryPlace() {
        return deliveryPlace;
    }

    public Status getStatus() {
        return status;
    }

    public void markPaid() {
        this.status = Status.PAID;
    }

    public void attachPayment(Payment payment) {
        this.payment = payment;
    }

    public void attachInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public void assignDeliveryPlace(OrderPlaces deliveryPlace) {
        this.deliveryPlace = deliveryPlace;
    }

    public void cancel() {
        this.status = Status.CANCELLED;
    }
}