package com.artx.artx.domain.user.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InquiryId implements Serializable {

	private UUID SellerId;
	private UUID BuyerId;

}
