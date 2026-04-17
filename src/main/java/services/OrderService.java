package services;

import exceptions.ExpiredFoodException;
import exceptions.InvalidPaymentException;
import models.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderService {
    private static final Logger logger = LogManager.getLogger(OrderService.class);


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

        try {
            List<Food> expiredItems = items.stream().filter(item -> {
                try {
                    return item.isExpired();
                } catch (ExpiredFoodException e) {
                    throw new RuntimeException(e);
                }
            }).toList();

            new ArrayList<>(expiredItems).forEach(item -> {
                logger.info("Cart contains expired food removing from cart");
                cartService.removeItem(item);
            });
        } catch (RuntimeException e) {
            if (e.getCause() instanceof ExpiredFoodException expiredFoodException) {
                throw expiredFoodException;
            }
            throw e;
        }

        BigDecimal total = cartService.calculateTotal();

        Map<Food, Integer> foodMap = customer.getCart()
                .getCartItems()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                food -> food, Collectors.collectingAndThen(
                                        Collectors.counting(), Long::intValue
                                )
                        )
                );

        Order order = new Order(customer, foodMap, total);
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
            logger.warn("Payment failed: " + e.getMessage());
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
