package com.artx.artx.cart.repository;

import com.artx.artx.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
