package com.artx.artx.customer.cart.entity;

import com.artx.artx.etc.error.ErrorCode;
import com.artx.artx.etc.exception.BusinessException;
import com.artx.artx.etc.model.BaseEntity;
import com.artx.artx.customer.product.entity.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartProduct extends BaseEntity {

	@EmbeddedId
	private CartProductId cartProductId;

	@MapsId("cartId")
	@ManyToOne(fetch = FetchType.LAZY)
	private Cart cart;

	@MapsId("productId")
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	private long quantity;

	@PrePersist
	public void prePersist(){
		if(this.cartProductId == null){
			this.cartProductId = CartProductId.from(cart, product);
		}
	}

	public static CartProduct from(Cart cart, Product product){

		return CartProduct.builder()
				.cart(cart)
				.product(product)
				.quantity(1L)
				.build();
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CartProduct cartProduct)) return false;
		return cart.equals(cartProduct.cart) && product.equals(cartProduct.product);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cart, product);
	}

	public void increase() {
		this.quantity++;
	}

	public void decrease() {
		if(this.quantity < 1){
			throw new BusinessException(ErrorCode.MUST_BE_MORE_THAN_ZERO);
		}
		this.quantity--;
	}

	public boolean isEmpty(){
		if(this.quantity <= 0){
			return true;
		}
		return false;
	}
}
