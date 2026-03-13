package models;

public class Support {
    public enum Status {
        OPEN,
        CLOSED
    }

    private final String customerName;
    private final String message;
    private Status status = Status.OPEN;
    private SupportResolution resolution;

    public Support(String customerName, String message) {
        this.customerName = customerName;
        this.message = message;
    }

    public String getCustomerName() {
        return customerName;
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
}
