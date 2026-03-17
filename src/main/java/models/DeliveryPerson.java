package models;

import java.util.Arrays;

public class DeliveryPerson extends Employee {

    private Address destinationAddress;
    private OrderPlaces[] orderPlaces;

    public DeliveryPerson(String name, String email, String number, String hashedPassword, String destinationAddress) {
        super(name, email, number, hashedPassword);
        this.destinationAddress = new Address("Unknown city", destinationAddress, "Delivery area");
        this.orderPlaces = new OrderPlaces[0];
    }

    public DeliveryPerson(String name, String email, String number, String hashedPassword, Address destinationAddress) {
        super(name, email, number, hashedPassword);
        this.destinationAddress = destinationAddress;
        this.orderPlaces = new OrderPlaces[0];
    }

    public void addOrder(OrderPlaces orderPlaces) {
        OrderPlaces[] next = Arrays.copyOf(this.orderPlaces, this.orderPlaces.length + 1);
        next[next.length - 1] = orderPlaces;
        this.orderPlaces = next;
    }

    public String getDestinationAddress() {
        return destinationAddress.toString();
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = new Address("Unknown city", destinationAddress, "Delivery area");
    }

    public void setDestinationAddress(Address destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public Address getDestinationAddressDetails() {
        return destinationAddress;
    }

    @Override
    public String getRoleName() {
        return "Delivery Person";
    }

    public OrderPlaces[] getOrderPlaces() {
        return Arrays.copyOf(orderPlaces, orderPlaces.length);
    }

}