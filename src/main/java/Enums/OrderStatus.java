package Enums;

public enum OrderStatus {

    CREATED("order created"),
    PAID("order paid"),
    CANCELLED("order cancelled");

    private final String description;

    OrderStatus(String desc) {
        this.description = desc;
    }

    public String getDescription() {
        return description;
    }
}
