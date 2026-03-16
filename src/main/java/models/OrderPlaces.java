package models;

public class OrderPlaces extends LocationInformation {

    private Order order;
    private String orderLocation;
    private String placeName;
    private Address address;

    public OrderPlaces(String orderLocation, String placeName) {
        super("Order Location");
        this.orderLocation = orderLocation;
        this.placeName = placeName;
        this.address = new Address("Unknown city", orderLocation, placeName);
    }

    public OrderPlaces(Address address, String placeName) {
        super("Order Location");
        this.address = address;
        this.placeName = placeName;
        this.orderLocation = address.fullAddress();
    }

    public OrderPlaces(Order order, Address address, String placeName) {
        this(address, placeName);
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getOrderLocation() {
        return orderLocation;
    }

    public void setOrderLocation(String orderLocation) {
        this.orderLocation = orderLocation;
        this.address = new Address("Unknown city", orderLocation, placeName);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(String city, String street, String details) {
        this.address = new Address(city, street, details);
        this.orderLocation = this.address.fullAddress();
    }

    public void setAddress(Address address) {
        this.address = address;
        this.orderLocation = address.fullAddress();
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    @Override
    public String formatLocation() {
        return address.fullAddress();
    }

    @Override
    public String toString() {
        return formatLocation() + " (" + placeName + ")";
    }
}
