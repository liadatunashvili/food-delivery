package models;

import java.util.ArrayList;
import java.util.List;

public class Customer extends ConsumerProfile {

    private static int counter;
    private final Cart cart;
    private Address deliveryAddress;
    private final int customerID;
    private List<Order> orders = new ArrayList<>();
    private final List<Invoice> invoices = new ArrayList<>();
    private final List<Support> supportTickets = new ArrayList<>();


    public Customer(String name, String email, String number, String hashedPassword, String address) {
        super(name, email, number, hashedPassword);
        this.deliveryAddress = new Address("Unknown city", address, "Customer default address");
        this.cart = new Cart(this);
        this.customerID = counter++;
    }

    public Customer(String name, String email, String number, String hashedPassword, Address deliveryAddress) {
        super(name, email, number, hashedPassword);
        this.deliveryAddress = deliveryAddress;
        this.cart = new Cart(this);
        this.customerID = counter++;
    }

    public Customer(String name, String email, String number, String hashedPassword, int customerID, List<Order> orders, String city, String street, String details) {
        super(name, email, number, hashedPassword);
        this.deliveryAddress = new Address(city, street, details);
        this.cart = new Cart(this);
        this.customerID = customerID;
        this.orders = orders == null ? new ArrayList<>() : orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
    }

    public void addInvoice(Invoice invoice) {
        invoices.add(invoice);
    }

    public void addSupportTicket(Support supportTicket) {
        supportTickets.add(supportTicket);
    }

    @Override
    public String getRoleName() {
        return "Customer";
    }

    public Cart getCart() {
        return cart;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public List<Support> getSupportTickets() {
        return supportTickets;
    }

    public String getAddress() {
        return deliveryAddress.toString();
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setDeliveryAddress(String city, String street, String details) {
        this.deliveryAddress = new Address(city, street, details);
    }

    public int getCustomerID() {

        return customerID;
    }
}
