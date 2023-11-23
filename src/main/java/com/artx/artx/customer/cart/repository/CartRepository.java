package com.artx.artx.customer.cart.repository;

import com.artx.artx.customer.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
