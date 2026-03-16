package org.example;

import java.math.BigDecimal;

import models.Address;
import models.Customer;
import models.DeliveryPerson;
import models.Food;
import models.Invoice;
import models.Order;
import models.OrderPlaces;
import models.Payment;
import models.SupportResolution;
import services.CartService;
import services.DeliveryService;
import services.OrderService;
import services.PaymentService;
import services.SupportService;

public class Main {

    public static void main(String[] args) {
        // setup models
        Address customerAddress = new Address("Tbilisi", "Gldani", "Building 5, Apt 12");
        Customer customer = new Customer("Lika", "likadatvi17@example.com", "+995568332112", "password", customerAddress);
        DeliveryPerson courier1 = new DeliveryPerson("Dato", "dato@example.com", "+995555555", "password", "gldani IV mikro");
        DeliveryPerson courier2 = new DeliveryPerson("Bob", "Bobbob@example.com", "+995111111", "password", "Varketili");

        // setup services
        CartService cartService = new CartService(customer.getCart());
        PaymentService paymentService = new PaymentService();
        DeliveryService deliveryService = new DeliveryService();
        OrderService orderService = new OrderService(cartService, paymentService, deliveryService);
        SupportService supportService = new SupportService();

        deliveryService.addDeliveryPerson(courier1);
        deliveryService.addDeliveryPerson(courier2);

        Food burger = new Food("Burger", BigDecimal.valueOf(8.50), 2);
        Food fries = new Food("Fries", BigDecimal.valueOf(3.00), 2);

        // cart operations
        cartService.addItem(burger);
        cartService.addItem(fries);
        System.out.println("CART TOTAL: " + cartService.calculateTotal());

        // create order
        Order order = orderService.createOrder(customer);
        System.out.println("ORDER CREATED: ORDER " + order.getId());

        // pay order
        Payment payment = orderService.payForOrder(order, Payment.Method.CARD);
        Invoice invoice = orderService.createInvoice(order);

        System.out.println("GOOD PAYMENT: " + payment.isSuccess() + "  :  " + payment.getAmount());
        if (invoice != null) {
            System.out.println("THIS IS THE INVOICE:" + invoice.generateSummary());
        }
        // finish Order this prints itself
        orderService.finishOrder(order);

        // now SOLO delivery stuff
        OrderPlaces dropOff = new OrderPlaces(order, customerAddress, "Alice's Place");
        deliveryService.assignDelivery(order, null, dropOff);

        // make a support ticket
        supportService.makeComplaint(customer, order, "My fries were cold :(");
        System.out.println("Open tickets: " + supportService.getTickets().length);
        System.out.println("Customer invoices: " + customer.getInvoices().length);
        System.out.println("Customer support history: " + customer.getSupportTickets().length);

        // resolve ticket
        SupportResolution resolution = supportService.resolveTicket(0, "No problem, we'll send a fresh portion.", "Lika");
        if (resolution != null) {
            System.out.println("Resolved by: " + resolution.getResolvedBy());
        }
        System.out.println("Open tickets: " + supportService.getTickets().length);
    }
}