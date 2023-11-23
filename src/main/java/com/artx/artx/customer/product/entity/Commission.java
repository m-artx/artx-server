package com.artx.artx.customer.product.entity;

import com.artx.artx.etc.model.BaseEntity;
import com.artx.artx.customer.product.type.CommissionStatus;
import com.artx.artx.common.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Commission extends BaseEntity {

	@EmbeddedId
	private CommissionId id;

	@MapsId("userId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_Id", nullable = false)
	private User user;

	@MapsId("productId")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_Id", nullable = false)
	private Product product;

	@Column(nullable = false)
	private String content;

	@Column(nullable = false)
	private String comment;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CommissionStatus status;

}
