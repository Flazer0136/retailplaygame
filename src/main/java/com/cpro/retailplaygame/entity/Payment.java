package com.cpro.retailplaygame.entity;  // This is where the Payment class is located

import java.util.Date;  // Import the Date class to handle dates
import jakarta.persistence.*;  // Import stuff for saving data in the database

@Entity  // Marks this class as an entity that we can save to a database
@Table(name = "payments")  // Links this class to the "payments" table in the database
public class Payment {

    @Id  // Tells the database that this is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Automatically generates a unique ID for each payment
    private Long id;

    @Column(nullable = false)  // Ensures this field can't be empty in the database
    private String paymentMethod;  // The method used for payment (like Credit Card, PayPal, etc.)

    @Column(nullable = false)  // Ensures this field can't be empty in the database
    private double amountPaid;  // The total amount that was paid

    @Temporal(TemporalType.DATE)  // Only stores the date (not the time)
    @Column(nullable = false)  // Ensures this field can't be empty in the database
    private Date paymentDate;  // The date when the payment was made

    @Column(nullable = false)  // Ensures this field can't be empty in the database
    private double taxAmount;  // The tax added to the payment

    // No-argument constructor (needed for JPA to work properly)
    public Payment() {
    }

    // Constructor to set all the fields when creating a new Payment
    public Payment(Long id, String paymentMethod, double amountPaid, Date paymentDate, double taxAmount) {
        this.id = id;
        this.paymentMethod = paymentMethod;
        this.amountPaid = amountPaid;
        this.paymentDate = paymentDate;
        this.taxAmount = taxAmount;
    }

    // Getters and Setters to access and update the fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }
}