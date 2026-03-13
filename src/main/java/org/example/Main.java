package org.example;

import models.*;
import services.CartService;
import services.DeliveryService;
import services.OrderService;
import services.PaymentService;
import services.SupportService;

import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        // setup services
        Cart cart = new Cart();
        CartService cartService = new CartService(cart);
        PaymentService paymentService = new PaymentService();
        DeliveryService deliveryService = new DeliveryService();
        OrderService orderService = new OrderService(cartService, paymentService, deliveryService);
        SupportService supportService = new SupportService();

        //setup models
        Address customerAddress = new Address("Tbilisi", "Gldani", "Building 5, Apt 12");
        Customer customer = new Customer("Lika", "likadatvi17@example.com", "+995568332112", "password", customerAddress);
        DeliveryPerson courier1 = new DeliveryPerson("Dato", "dato@example.com", "+995555555", "password", "gldani IV mikro");
        DeliveryPerson courier2 = new DeliveryPerson("Bob", "Bobbob@example.com", "+995111111", "password", "Varketili");
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
        Invoice invoice = new Invoice(order, payment);

        System.out.println("GOOD PAYMENT: " + payment.isSuccess() + "  :  " + payment.getAmount());
        System.out.println("THIS IS THE INVOICE:" + invoice.generateSummary());
        // finish Order this prints itself
        orderService.finishOrder(order, payment);

        // now SOLO delivery stuff
        OrderPlaces dropOff = new OrderPlaces(customerAddress, "Alice's Place");
        deliveryService.assignDelivery(order, null, dropOff);

        // make a support ticket
        supportService.makeComplaint(customer, "My fries were cold :(");
        System.out.println("Total tickets: " + supportService.getTickets().length);

        //resolve ticket
        SupportResolution resolution = supportService.resolveTicket(0, "No problem, we'll send a fresh portion.", "Lika");
        if (resolution != null) {
            System.out.println("Resolved by: " + resolution.getResolvedBy());
        }
        System.out.println("Total tickets: " + supportService.getTickets().length);

    }
}