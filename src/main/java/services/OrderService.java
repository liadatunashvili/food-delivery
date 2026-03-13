package services;

import java.math.BigDecimal;

import models.*;

public class OrderService {
    private final CartService cartService;
    private final PaymentService paymentService;
    private DeliveryService deliveryService;

    public OrderService(CartService cartService, PaymentService paymentService,DeliveryService deliveryService) {
        this.cartService = cartService;
        this.paymentService = paymentService;
        this.deliveryService = deliveryService;
    }

    public Order createOrder(Customer customer) {
        Food[] items = cartService.viewItems();

        for(Food item : items){
            if (item.isExpired()){
                System.out.println("Cart contains expired food removing from cart");
                cartService.removeItem(item);
            }
        }
        BigDecimal total = cartService.calculateTotal();
        Order order = new Order(customer, items, total);
        customer.addOrder(order);
        return order;
    }

    public Payment payForOrder(Order order, Payment.Method method) {
        Payment payment = paymentService.processPayment(order.getTotal(), method);
        if (payment.isSuccess()) {
            order.markPaid();
        }
        return payment;
    }

    public void finishOrder(Order order, Payment payment){
        OrderPlaces orderPlaces = new OrderPlaces(order.getCustomer().getDeliveryAddress(), order.getCustomer().getName() + "'s place");
        deliveryService.assignDelivery(order, null,orderPlaces);


    }
}
