package com.artx.artx.cart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
	private List<CartItem> cartItems;

	public void addCartItem(CartItem cartItem) {
		cartItem.setCart(this);

		if(this.cartItems == null){
			this.cartItems = new ArrayList<>();
		}
		this.cartItems.add(cartItem);
	}
}


