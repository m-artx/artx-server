package com.artx.artx.cart.repository;

import com.artx.artx.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {


	@Query(
			"SELECT c FROM Cart c " +
					"LEFT JOIN FETCH c.cartItems ci " +
					"LEFT JOIN FETCH ci.product cip " +
					"WHERE c.id = :cartId"
	)
	Optional<Cart> readCartWithCartItemAndProductByCartId(@Param("cartId") Long cartId);
}
