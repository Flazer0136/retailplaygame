//package com.cpro.retailplaygame.service.impl;
//
//import com.cpro.retailplaygame.entity.Cart;
//import com.cpro.retailplaygame.entity.CartItem;
//import com.cpro.retailplaygame.entity.Product;
//import com.cpro.retailplaygame.entity.User;
//import com.cpro.retailplaygame.repository.CartRepository;
//import com.cpro.retailplaygame.repository.CartItemRepository;
//import com.cpro.retailplaygame.repository.ProductRepository;
//import com.cpro.retailplaygame.repository.UserRepository;
//import com.cpro.retailplaygame.service.CartService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CartServiceImpl implements CartService {
//
//    @Autowired
//    private CartRepository cartRepository;
//
//    @Autowired
//    private CartItemRepository cartItemRepository;
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private UserRepository userRepository; // Inject the UserRepository
//
//    // Implementation of the method to add a product to the cart
//    @Override
//    public Cart addProductToCart(Long userId, Long productId, int quantity) {
//        // Fetch the user's cart (or create a new one if it doesn't exist)
//        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
//        Cart cart = cartRepository.findByUserId(userId)
//                .orElse(new Cart(user)); // Assuming you have a constructor in Cart that accepts User
//
//        // Fetch the product
//        Product product = productRepository.findById(productId)
//                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
//
//        // Check if the product already exists in the cart
//        CartItem existingCartItem = cartItemRepository.findByCartAndProduct(cart, product);
//
//        if (existingCartItem != null) {
//            // If the product exists, just update the quantity
//            existingCartItem.setQuantity(existingCartItem.getQuantity() + quantity);
//            cartItemRepository.save(existingCartItem);
//        } else {
//            // If it's a new product, add it as a new CartItem
//            CartItem newCartItem = new CartItem(cart, product, quantity);
//            cart.addCartItem(newCartItem);
//            cartItemRepository.save(newCartItem);
//        }
//
//        // Save the cart (this will also save the CartItems because of CascadeType.ALL)
//        cartRepository.save(cart);
//        return cart;
//    }
//
//    @Override
//    public Cart getCartByUserId(Long userId) {
//        // Fetch the user
//        userRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//        // Fetch the cart associated with the user ID
//        return cartRepository.findByUserId(userId)
//                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user ID " + userId));
//    }
//
//    @Override
//    public Cart checkout(Long userId) {
//        // Fetch the user's cart
//        Cart cart = getCartByUserId(userId);
//
//        // Iterate through cart items to update quantities in the product repository
//        for (CartItem cartItem : cart.getCartItems()) {
//            // Fetch the product associated with the cart item
//            Product product = productRepository.findById(cartItem.getProduct().getProductID())
//                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
//
//            // Check if there's enough quantity for the order
//            if (product.getQuantity() < cartItem.getQuantity()) {
//                throw new IllegalArgumentException("Not enough stock for product: " + product.getProductName());
//            }
//
//            // Decrease the quantity of the product after checkout
//            product.setQuantity(product.getQuantity() - cartItem.getQuantity());
//            productRepository.save(product);  // Save the updated product
//        }
//
//        // Optionally, empty the cart after checkout
//        cart.getCartItems().clear();
//        cartRepository.save(cart);  // Save the cart with cleared items
//
//        return cart;  // Return the cart after checkout (or you can return a success message)
//    }
//
//}
