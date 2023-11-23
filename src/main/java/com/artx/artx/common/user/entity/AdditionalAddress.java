package com.artx.artx.common.user.entity;

import com.artx.artx.etc.model.Address;
import com.artx.artx.etc.model.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalAddress extends BaseEntity {

	@EmbeddedId
	private AdditionalAddressId id;

	@MapsId("userId")
	@JoinColumn(name = "user_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@Embedded
	private Address address;

}
