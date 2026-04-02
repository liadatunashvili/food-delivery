package models;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

public class Order {

    private static int counter;
    private final int id;
    private final Customer customer;
    private final Map<Food, Integer> items;
    private final BigDecimal total;
    private Payment payment;
    private Invoice invoice;
    private OrderPlaces deliveryPlace;
    private Status status;

    public Order(Customer customer, Map<Food, Integer> items, BigDecimal total) {
        this.id = ++counter;
        this.customer = customer;
        this.items = items;
        this.total = total;
        this.status = Status.CREATED;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Map<Food, Integer> getItems() {
        return Collections.unmodifiableMap(items);
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

    public enum Status {
        CREATED, PAID, CANCELLED
    }
}