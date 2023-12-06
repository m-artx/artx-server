package com.artx.artx.domain.cart.domain;

import com.artx.artx.domain.model.BaseEntity;
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
public class Cart extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
	private List<CartProduct> cartProducts;

	public void addCartProduct(CartProduct cartProduct) {
		cartProduct.setCart(this);

		if(this.cartProducts == null){
			this.cartProducts = new ArrayList<>();
		}
		this.cartProducts.add(cartProduct);
	}
}


