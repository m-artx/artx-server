package com.artx.artx.product.entity;

import com.artx.artx.product.type.CommissionStatus;
import com.artx.artx.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Commission {

	@EmbeddedId
	private CommissionId id;

	@MapsId("userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_Id")
	private User user;

	@MapsId("productId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_Id")
	private Product product;

	private String content;
	private String comment;

	@Enumerated(EnumType.STRING)
	private CommissionStatus status;

}
