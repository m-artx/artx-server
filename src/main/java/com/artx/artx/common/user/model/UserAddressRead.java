package com.artx.artx.common.user.model;

import com.artx.artx.common.user.entity.User;
import com.artx.artx.common.user.entity.UserAddress;
import com.artx.artx.etc.model.Address;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class UserAddressRead {

	@Builder
	@Getter
	public static class Response {
		private Address defaultAddress;
		private List<AddressResponse> addresses;

		public static Response of(User user) {

			return Response.builder()
					.defaultAddress(user.getDefaultAddress().getAddress())
					.addresses(user.getUserAddresses().stream()
							.map(AddressResponse::of)
							.collect(Collectors.toList())).build();
		}
	}

	@Getter
	@Builder
	public static class AddressResponse implements Serializable {

		private Long addressId;
		private String address;
		private String addressDetail;

		public static AddressResponse of(UserAddress userAddress) {
			return AddressResponse.builder()
					.addressId(userAddress.getId())
					.address(userAddress.getAddress().getAddress())
					.addressDetail(userAddress.getAddress().getAddressDetail())
					.build();
		}

	}

}
