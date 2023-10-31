package com.artx.artx.cart.repository;

import com.artx.artx.cart.model.CartItem;
import com.artx.artx.cart.model.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {

	@Modifying
	@Query("DELETE FROM CartItem c WHERE c.cart.id = :cartId AND c.product.id IN :productIds")
	void deleteAllByCartIdAndProductIds(@Param("cartId") Long cartId, @Param("productIds") List<Long> productIds);
}
