package com.cpro.retailplaygame.service.impl;  // This is where the PaymentServiceImpl class is located

import com.cpro.retailplaygame.entity.Payment;  // Import the Payment entity to work with payment data
import com.cpro.retailplaygame.repository.PaymentRepository;  // Import the repository to interact with the database
import com.cpro.retailplaygame.service.PaymentService;  // Import the PaymentService interface to implement its methods
import org.springframework.stereotype.Service;  // Annotation to mark this class as a service in Spring

import java.util.List;  // Import the List class to store multiple payments
import java.util.Optional;  // Import Optional to handle potentially empty results

// This class provides the implementation of the PaymentService interface
@Service  // Marks this class as a Spring service to be managed by Spring's IoC container
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;  // Repository to interact with the payment database

    // Constructor for dependency injection
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;  // Assign the repository to the instance variable
    }

    @Override
    public Payment createPayment(Payment payment) {
        // Save the payment to the database and return the saved payment object
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPaymentById(Long paymentId) {
        // Use the repository to find a payment by its ID
        Optional<Payment> optionalPayment = paymentRepository.findById(paymentId);
        // Return the payment if found, otherwise return null
        return optionalPayment.orElse(null);
    }

    @Override
    public List<Payment> getAllPayments() {
        // Get all the payments from the database
        return paymentRepository.findAll();
    }

    @Override
    public Payment updatePayment(Payment payment) {
        // Find the payment by its ID to check if it exists
        Optional<Payment> existingPaymentOptional = paymentRepository.findById(payment.getId());
        if (existingPaymentOptional.isPresent()) {
            // If the payment exists, update its details and save it back to the database
            Payment existingPayment = existingPaymentOptional.get();
            existingPayment.setPaymentMethod(payment.getPaymentMethod());
            existingPayment.setAmountPaid(payment.getAmountPaid());
            existingPayment.setPaymentDate(payment.getPaymentDate());
            existingPayment.setTaxAmount(payment.getTaxAmount());
            return paymentRepository.save(existingPayment);
        }
        // If the payment is not found, return null
        return null;
    }

    @Override
    public void deletePayment(Long paymentId) {
        // Delete the payment from the database by its ID
        paymentRepository.deleteById(paymentId);
    }
}
