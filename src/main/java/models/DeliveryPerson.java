package models;

import enums.UserRole;

import java.util.HashSet;
import java.util.Set;

public class DeliveryPerson extends Employee {

    private final Set<OrderPlaces> orderPlaces;
    private Address destinationAddress;

    public DeliveryPerson(String name, String email, String number, String hashedPassword, String destinationAddress) {
        super(name, email, number, hashedPassword);
        this.destinationAddress = new Address("Unknown city", destinationAddress, "Delivery area");
        this.orderPlaces = new HashSet<>();
    }

    public DeliveryPerson(String name, String email, String number, String hashedPassword, Address destinationAddress) {
        super(name, email, number, hashedPassword);
        this.destinationAddress = destinationAddress;
        this.orderPlaces = new HashSet<>();
    }

    public void addOrder(OrderPlaces orderPlaces) {
        this.orderPlaces.add(orderPlaces);
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

    public UserRole getUserRole() {
        return UserRole.COURIER;
    }

    public Set<OrderPlaces> getOrderPlaces() {
        return orderPlaces;
    }

}