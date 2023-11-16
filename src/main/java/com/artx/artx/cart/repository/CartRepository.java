package com.artx.artx.cart.repository;

import com.artx.artx.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {


	@Query(
			"SELECT c FROM Cart c " +
					"LEFT JOIN FETCH c.cartProducts cp " +
					"LEFT JOIN FETCH cp.product cpp " +
					"LEFT JOIN FETCH cpp.user cppu " +
					"WHERE c.id = :cartId"
	)
	Optional<Cart> readCartWithCartProductAndProductByCartId(@Param("cartId") Long cartId);
}
