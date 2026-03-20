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
import models.RoleDescribable;
import models.SupportResolution;
import services.CartOperations;
import services.CartService;
import services.DeliveryAssigner;
import services.DeliveryService;
import services.OrderService;
import services.PaymentProcessor;
import services.PaymentService;
import services.SupportService;
import services.TicketResolver;

public class Main {

    public static void main(String[] args) {
        // setup models
        Address customerAddress = new Address("Tbilisi", "Gldani", "Building 5, Apt 12");
        Customer customer = new Customer("Lika", "likadatvi17@example.com", "+995568332112", "password", customerAddress);
        DeliveryPerson courier1 = new DeliveryPerson("Dato", "dato@example.com", "+995555555", "password", "gldani IV mikro");
        DeliveryPerson courier2 = new DeliveryPerson("Bob", "Bobbob@example.com", "+995111111", "password", "Varketili");

        // setup services
        CartOperations cartService = new CartService(customer.getCart());
        PaymentProcessor paymentService = new PaymentService();
        DeliveryService deliveryService = new DeliveryService();
        DeliveryAssigner deliveryAssigner = deliveryService;
        OrderService orderService = new OrderService(cartService, paymentService, deliveryAssigner);
        SupportService supportService = new SupportService();
        TicketResolver ticketResolver = supportService;
        RoleDescribable currentMember = customer;

        deliveryService.addDeliveryPerson(courier1);
        deliveryService.addDeliveryPerson(courier2);

        Food burger = new Food("Burger", BigDecimal.valueOf(8.50), 2);
        Food fries = new Food();
        fries.setName("Fries");
        fries.setFoodprice(BigDecimal.valueOf(3.00));
        fries.setExpiration(1);

        // cart operations
        cartService.addItem(burger);
        cartService.addItem(fries);
        System.out.println(CartService.SERVICE_NAME + " TOTAL: " + cartService.calculateTotal());
        System.out.println("CURRENT MEMBER: " + currentMember.getRoleName());
        System.out.println("MEMBER STATE: " + customer.describeMemberState());

        // create order
        Order order = orderService.createOrder(customer);
        System.out.println("ORDER CREATED: ORDER " + order.getId());

        // pay order
        Payment payment = orderService.payForOrder(order, Payment.Method.CARD);
        System.out.println("GOOD PAYMENT: " + payment.isSuccess() + "  :  " + payment.getAmount());
        System.out.println("PAYMENT RECORD: " + orderService.describeFinancialRecord(payment));
        System.out.println("LATEST RECORD AFTER PAYMENT: " + orderService.describeLatestRecord());

        Invoice invoice = orderService.createInvoice(order);
        if (invoice != null) {
            System.out.println("THIS IS THE INVOICE:" + invoice.generateSummary());
            System.out.println("INVOICE RECORD: " + orderService.describeFinancialRecord(invoice));
            System.out.println("LATEST RECORD AFTER INVOICE: " + orderService.describeLatestRecord());
        }
        // finish Order this prints itself
        orderService.finishOrder(order);

        // now SOLO delivery stuff
        OrderPlaces dropOff = new OrderPlaces();
        dropOff.setAddress(customerAddress);
        dropOff.setPlaceName("Alice's Place");
        dropOff.setOrder(order);
        deliveryAssigner.assignDelivery(order, null, dropOff);

        // make a support ticket
        supportService.setCurrentActor(customer);
        System.out.println("CURRENT ACTOR: " + supportService.describeCurrentActor());
        supportService.setCurrentActor(courier1);
        System.out.println("CURRENT ACTOR: " + supportService.describeCurrentActor());

        supportService.makeComplaint(customer, order, "My fries were cold :(");
        System.out.println("Open tickets: " + supportService.getTickets().length);
        System.out.println("Customer invoices: " + customer.getInvoices().length);
        System.out.println("Customer support history: " + customer.getSupportTickets().length);

        // resolve ticket
        supportService.setCurrentActor(courier1);
        SupportResolution resolution = ticketResolver.resolveTicket(0, "No problem, we'll send a fresh portion.");
        if (resolution != null) {
            System.out.println("Resolved by: " + resolution.getResolvedByLabel());
        }
        System.out.println("Open tickets: " + supportService.getTickets().length);
    }
}