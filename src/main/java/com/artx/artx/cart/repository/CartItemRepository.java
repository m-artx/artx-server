package com.artx.artx.cart.repository;

import com.artx.artx.cart.model.CartItem;
import com.artx.artx.cart.model.CartItemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, CartItemId> {

	@Modifying
	@Query("DELETE FROM CartItem c WHERE c.cart.id = :cartId AND c.product.id IN :productIds")
	void deleteAllByCartIdAndProductIds(@Param("cartId") Long cartId, @Param("productIds") List<Long> productIds);

	@Modifying
	@Query("DELETE FROM CartItem c WHERE c.createdAt < :thirtyDaysAgo")
	void deleteExpiredItems(@Param("thirtyDaysAgo") LocalDateTime thirtyDaysAgo);

	@Modifying
	@Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId AND ci.product.id IN :productIds")
	void deleteSelectedCartItemsByCartIdAndProductIds(@Param("cartId")Long cartId, @Param("productIds")List<Long> productIds);
}
