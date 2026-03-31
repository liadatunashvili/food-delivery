package enums;

public enum PaymentType {

    CARD("card"),
    CASH("cash");

    private final String type;

    PaymentType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
