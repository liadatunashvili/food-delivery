package models;

public class Support {

    private final Customer customer;
    private final Order relatedOrder;
    private final String message;
    private Status status = Status.OPEN;
    private SupportResolution resolution;
    public Support(Customer customer, String message) {
        this(customer, null, message);
    }

    public Support(Customer customer, Order relatedOrder, String message) {
        this.customer = customer;
        this.relatedOrder = relatedOrder;
        this.message = message;
    }

    public String getCustomerName() {
        return customer.getName();
    }

    public Customer getCustomer() {
        return customer;
    }

    public Order getRelatedOrder() {
        return relatedOrder;
    }

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }

    public SupportResolution getResolution() {
        return resolution;
    }

    public void close(SupportResolution resolution) {
        this.resolution = resolution;
        this.status = Status.CLOSED;
    }

    public void close() {
        this.status = Status.CLOSED;
    }

    public enum Status {
        OPEN,
        CLOSED
    }
}
