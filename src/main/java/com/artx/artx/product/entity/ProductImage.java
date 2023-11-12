package com.artx.artx.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductImage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String type;
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private Product product;

	public static ProductImage from(MultipartFile multipartFile){
		return ProductImage.builder()
				.type(multipartFile.getContentType())
				.name(multipartFile.getName())
				.build();
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
