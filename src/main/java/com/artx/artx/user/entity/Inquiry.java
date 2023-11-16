package com.artx.artx.user.entity;

import com.artx.artx.common.model.BaseEntity;
import com.artx.artx.user.type.InquiryStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Inquiry extends BaseEntity {

	@EmbeddedId
	private InquiryId id;

	@MapsId("buyerId")
	@ManyToOne(fetch = FetchType.LAZY)
	private User buyer;

	@MapsId("sellerId")
	@ManyToOne(fetch = FetchType.LAZY)
	private User seller;

	private String title;
	private String content;
	private String comment;
	private InquiryStatus status;

}
