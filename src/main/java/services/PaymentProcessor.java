package services;

import models.Order;
import models.Payment;

public interface PaymentProcessor {

    Payment processPayment(Order order, Payment.Method method) throws InvalidPaymentException;
}

