package com.artx.artx.cart.repository;

import com.artx.artx.cart.entity.CartProduct;
import com.artx.artx.cart.entity.CartProductId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct, CartProductId> {

	@Modifying
	@Query("DELETE FROM CartProduct c WHERE c.cart.id = :cartId AND c.product.id IN :productIds")
	void deleteAllByCartIdAndProductIds(Long cartId, List<Long> productIds);

	@Modifying
	@Query("DELETE FROM CartProduct c WHERE c.createdAt < :thirtyDaysAgo")
	void deleteExpiredProducts(@Param("thirtyDaysAgo") LocalDateTime thirtyDaysAgo);

	@Modifying
	@Query("DELETE FROM CartProduct ci WHERE ci.cart.id = :cartId AND ci.product.id IN :productIds")
	void deleteSelectedCartProductsByCartIdAndProductIds(Long cartId, List<Long> productIds);

	@Query("SELECT cp FROM CartProduct cp WHERE cp.cart.id = :cartId ORDER BY cp.createdAt DESC ")
	Page<CartProduct> findAllByCart_Id(Long cartId, Pageable pageable);
}
