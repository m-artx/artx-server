package com.artx.artx.cart.model;

import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.common.model.BaseEntity;
import com.artx.artx.product.entity.Product;
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
public class CartItem extends BaseEntity {

	@EmbeddedId
	private CartItemId cartItemId;

	@MapsId("cartId")
	@ManyToOne(fetch = FetchType.LAZY)
	private Cart cart;

	@MapsId("productId")
	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	private Long quantity;

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CartItem cartItem)) return false;
		return cart.equals(cartItem.cart) && product.equals(cartItem.product);
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
