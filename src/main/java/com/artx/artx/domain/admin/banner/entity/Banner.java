package com.artx.artx.domain.admin.banner.entity;

import com.artx.artx.domain.model.BaseEntity;
import com.artx.artx.domain.product.domain.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Banner extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String link;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product")
	private Product product;

	public static Banner from(String productApiAddress, Product product){
		return Banner.builder()
				.product(product)
				.link(productApiAddress + product.getId())
				.build();
	}

}
