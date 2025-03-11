package com.cpro.retailplaygame.controller;  // Package where the PaymentController class is located

import com.cpro.retailplaygame.entity.Payment;  // Import the Payment entity for handling payment data
import com.cpro.retailplaygame.service.PaymentService;  // Import the PaymentService to perform business logic operations
import org.springframework.http.HttpStatus;  // Import HttpStatus to set HTTP status codes in responses
import org.springframework.http.ResponseEntity;  // Import ResponseEntity to handle HTTP responses
import org.springframework.web.bind.annotation.*;  // Import Spring annotations for creating REST APIs

import java.util.List;  // Import List to return multiple payments

// This class handles the REST API endpoints for the payment operations
@RestController  // Marks this class as a REST controller for Spring to handle HTTP requests
@RequestMapping("api/payments")  // Sets the base URL for all methods in this controller
public class PaymentController {

    private final PaymentService paymentService;  // Service to interact with the payment-related logic

    // Constructor for dependency injection to provide the PaymentService
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Create Payment REST API
    // Handles POST requests to create a new payment
    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentService.createPayment(payment);  // Save the payment using the service
        // Return the saved payment with HTTP status 201 (Created)
        return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
    }

    // Get Payment by ID REST API
    // Handles GET requests to retrieve a payment by its ID
    // Example URL: http://localhost:8080/api/payments/1
    @GetMapping("{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable("id") Long paymentId) {
        Payment payment = paymentService.getPaymentById(paymentId);  // Fetch payment by ID using the service
        // Return the payment with HTTP status 200 (OK)
        return new ResponseEntity<>(payment, HttpStatus.OK);
    }

    // Get All Payments REST API
    // Handles GET requests to retrieve all payments
    // Example URL: http://localhost:8080/api/payments
    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();  // Get the list of all payments using the service
        // Return the list of payments with HTTP status 200 (OK)
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    // Update Payment REST API
    // Handles PUT requests to update an existing payment
    // Example URL: http://localhost:8080/api/payments/1
    @PutMapping("{id}")
    public ResponseEntity<Payment> updatePayment(@PathVariable("id") Long paymentId,  // Payment ID in URL
                                                 @RequestBody Payment payment) {  // Payment data in request body
        payment.setId(paymentId);  // Set the ID of the payment to be updated
        Payment updatedPayment = paymentService.updatePayment(payment);  // Update the payment using the service
        // Return the updated payment with HTTP status 200 (OK)
        return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
    }

    // Delete Payment REST API
    // Handles DELETE requests to remove a payment by its ID
    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePayment(@PathVariable("id") Long paymentId) {
        paymentService.deletePayment(paymentId);  // Delete the payment using the service
        // Return a success message with HTTP status 200 (OK)
        return new ResponseEntity<>("Payment successfully deleted!", HttpStatus.OK);
    }
}
