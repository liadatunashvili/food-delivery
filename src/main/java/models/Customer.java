package models;

import java.util.Arrays;

public class Customer extends User {

    private static int counter;

    private Address deliveryAddress;
    private final Cart cart;
    private int customerID;
    private Order[] orders = new Order[0];
    private Invoice[] invoices = new Invoice[0];
    private Support[] supportTickets = new Support[0];

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

    public Customer(String name, String email, String number, String hashedPassword, int customerID, Order[] orders,
                    String city, String street, String details) {
        super(name, email, number, hashedPassword);
        this.deliveryAddress = new Address(city, street, details);
        this.cart = new Cart(this);
        this.customerID = customerID;
        this.orders = Arrays.copyOf(orders, orders.length);
    }

    public void addOrder(Order order) {
        Order[] next = Arrays.copyOf(orders, orders.length + 1);
        next[next.length - 1] = order;
        orders = next;
    }

    public void addInvoice(Invoice invoice) {
        Invoice[] next = Arrays.copyOf(invoices, invoices.length + 1);
        next[next.length - 1] = invoice;
        invoices = next;
    }

    public void addSupportTicket(Support supportTicket) {
        Support[] next = Arrays.copyOf(supportTickets, supportTickets.length + 1);
        next[next.length - 1] = supportTicket;
        supportTickets = next;
    }

    public Cart getCart() {
        return cart;
    }

    public Order[] getOrders() {
        return Arrays.copyOf(orders, orders.length);
    }

    public Invoice[] getInvoices() {
        return Arrays.copyOf(invoices, invoices.length);
    }

    public Support[] getSupportTickets() {
        return Arrays.copyOf(supportTickets, supportTickets.length);
    }

    public String getAddress() {
        return deliveryAddress.toString();
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String city, String street, String details) {
        this.deliveryAddress = new Address(city, street, details);
    }


    public void setDeliveryAddress(Address deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public int getCustomerID() {
        return customerID;
    }
}
