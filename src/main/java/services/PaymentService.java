package services;

import exceptions.InvalidPaymentException;
import models.Order;
import models.Payment;

public class PaymentService implements PaymentProcessor {

    public final Payment processPayment(Order order, Payment.Method method) throws InvalidPaymentException {
        if (method == null || order == null) {
            throw new InvalidPaymentException("Payment method is invalid");
        }
        Payment payment = new Payment(order, order.getTotal(), method);
        payment.markSuccess();
        return payment;

    }
}
