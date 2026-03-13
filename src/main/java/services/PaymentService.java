package services;

import java.math.BigDecimal;
import models.Payment;

public class PaymentService {
    public Payment processPayment(BigDecimal amount, Payment.Method method) {
        Payment payment = new Payment(amount, method);
        payment.markSuccess();
        return payment;
    }
}
