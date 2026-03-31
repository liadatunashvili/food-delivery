package services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import exceptions.ExpiredFoodException;
import exceptions.InvalidPaymentException;
import models.*;

public class OrderService {

    private final CartOperations cartService;
    private final PaymentProcessor paymentService;
    private final DeliveryAssigner deliveryService;
    private FinantialRecord latestRecord;

    public OrderService(CartOperations cartService, PaymentProcessor paymentService, DeliveryAssigner deliveryService) {
        this.cartService = cartService;
        this.paymentService = paymentService;
        this.deliveryService = deliveryService;
    }

    public Order createOrder(Customer customer) throws ExpiredFoodException {
        List<Food> items = customer.getCart().getCartItems();

        for (Food item : items) {
            if (item.isExpired()) {
                System.out.println("Cart contains expired food removing from cart");
                cartService.removeItem(item);
            }
        }
        BigDecimal total = cartService.calculateTotal();
        Order order = new Order(customer, (Map<Food, Integer>) customer.getCart().getCartItems(), total);
        customer.addOrder(order);
        customer.getCart().clear();
        return order;
    }

    public Payment payForOrder(Order order, Payment.Method method) {
        try {
            Payment payment = paymentService.processPayment(order, method);
            order.attachPayment(payment);
            latestRecord = payment;
            if (payment.isSuccess()) {
                order.markPaid();
            }
            return payment;
        } catch (InvalidPaymentException e) {
            System.out.println("Payment failed: " + e.getMessage());
            return null;

        }
    }

    public Invoice createInvoice(Order order) {
        if (order.getPayment() == null) {
            return null;
        }
        Invoice invoice = new Invoice(order, order.getPayment());
        order.attachInvoice(invoice);
        order.getCustomer().addInvoice(invoice);
        latestRecord = invoice;
        return invoice;
    }

    public String describeFinancialRecord(FinantialRecord record) {
        return record.describeRecord();
    }

    public String describeLatestRecord() {
        if (latestRecord == null) {
            return "No financial record yet";
        }
        return latestRecord.describeRecord();
    }

    public void finishOrder(Order order) {
        OrderPlaces orderPlaces = new OrderPlaces(order, order.getCustomer().getDeliveryAddress(), order.getCustomer().getName() + "'s place");
        order.assignDeliveryPlace(orderPlaces);
        deliveryService.assignDelivery(order, null, orderPlaces);
    }
}
