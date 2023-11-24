package com.artx.artx.etc.model;

import com.artx.artx.common.user.entity.UserAddress;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Address {
	private String address;
	private String addressDetail;

	public static Address of(UserAddress userAddress) {
		return Address.builder()
				.address(userAddress.getAddress().getAddress())
				.addressDetail(userAddress.getAddress().getAddressDetail())
				.build();
	}
}
