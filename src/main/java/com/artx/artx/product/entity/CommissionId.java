package com.artx.artx.product.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class CommissionId implements Serializable {

	private UUID userId;
	private Long productID;

}
