package Enums;

public enum UserRole {

    CUSTOMER("customer"),
    COURIER("DeliveryPerson"),
    ADMIN("admin");

    private final String label;

    UserRole(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
