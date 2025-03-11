package com.cpro.retailplaygame.service;  // This is where the PaymentService interface is located

import com.cpro.retailplaygame.entity.Payment;  // Import the Payment class to use it here
import java.util.List;  // Import the List class to store multiple payments

// This is the PaymentService interface that defines the methods related to payment operations
public interface PaymentService {

    // Method to create a new payment
    Payment createPayment(Payment payment);

    // Method to get a payment by its unique ID
    Payment getPaymentById(Long paymentId);

    // Method to get a list of all payments
    List<Payment> getAllPayments();

    // Method to update an existing payment
    Payment updatePayment(Payment payment);

    // Method to delete a payment by its unique ID
    void deletePayment(Long paymentId);
}
