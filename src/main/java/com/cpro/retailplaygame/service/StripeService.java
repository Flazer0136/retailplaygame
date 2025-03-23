package com.cpro.retailplaygame.service;

import com.cpro.retailplaygame.entity.Cart;
import com.stripe.exception.StripeException;

public interface StripeService {
    String createCheckoutSession(Cart cart) throws StripeException;
}
