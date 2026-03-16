package services;

import models.Order;
import models.Payment;

public class PaymentService {

    public Payment processPayment(Order order, Payment.Method method) {
        Payment payment = new Payment(order, order.getTotal(), method);
        payment.markSuccess();
        return payment;
    }
}
