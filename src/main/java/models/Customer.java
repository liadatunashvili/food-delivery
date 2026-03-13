package models;

import java.util.Arrays;

public class Customer extends User {
    private Address deliveryAddress;
    private int customerID;
    private static int counter;
    private Order[] orders = new Order[0];

    public Customer(String name, String email, String number, String hashedPassword, String address) {
        super(name, email, number, hashedPassword);
        this.deliveryAddress = new Address("Unknown city", address, "Customer default address");
        this.customerID = counter++;
    }

    public Customer(String name, String email, String number, String hashedPassword, Address deliveryAddress) {
        super(name, email, number, hashedPassword);
        this.deliveryAddress = deliveryAddress;
        this.customerID = counter++;
    }

    public Customer(String name, String email, String number, String hashedPassword, int customerID, Order[] orders,String city, String street,String details ) {
        super(name, email, number, hashedPassword);
        this.deliveryAddress = new Address(city,street,details);
        this.customerID = customerID;
        this.orders = orders;
    }

    public void addOrder(Order order){
        Order[] next = Arrays.copyOf(orders, orders.length + 1);
        next[next.length - 1] = order;
        orders = next;
    }

    public Order[] getOrders() {
        return this.orders;
    }

    public String getAddress() {
        return this.deliveryAddress.toString();
    }


    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String city, String street, String details) {
        this.deliveryAddress = new Address(city,street,details);
    }


    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public int getCustomerID() {
        return customerID;
    }
}
