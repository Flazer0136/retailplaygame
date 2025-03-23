package com.cpro.retailplaygame.controller;

import com.cpro.retailplaygame.entity.Cart;
import com.cpro.retailplaygame.service.StripeService;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stripe")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/create-checkout-session")
    public String createCheckoutSession(@RequestBody Cart cart) throws StripeException {
        return stripeService.createCheckoutSession(cart);
    }
}
