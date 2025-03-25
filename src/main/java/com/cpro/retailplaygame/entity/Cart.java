package com.cpro.retailplaygame.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartID;

    @OneToOne
    @JoinColumn(name = "userID", referencedColumnName = "userID", nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems;

    @ManyToOne
    @JoinColumn(name = "couponId", referencedColumnName = "couponId", nullable = true)
    private Coupon coupon;

    public Cart() {}

    public Cart(User user) {
        this.user = user;
    }

    public Long getCartID() {
        return cartID;
    }

    public void setCartID(Long cartID) {
        this.cartID = cartID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    // Add a helper method to add a CartItem to the cart
    public void addCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
    }

    // Coupon getter/setter
    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }
}
