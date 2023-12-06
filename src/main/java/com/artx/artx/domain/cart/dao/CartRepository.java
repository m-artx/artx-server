package com.artx.artx.domain.cart.dao;

import com.artx.artx.domain.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
