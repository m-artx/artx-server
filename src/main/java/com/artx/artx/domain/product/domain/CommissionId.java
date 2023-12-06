package com.artx.artx.domain.product.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CommissionId implements Serializable {

	private UUID userId;
	private Long productId;

}
