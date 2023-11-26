package com.artx.artx.user.model;

import com.artx.artx.user.entity.User;
import com.artx.artx.user.entity.UserAddress;
import com.artx.artx.common.model.Address;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserAddressRead {

	@Builder
	@Getter
	public static class Response {
		private Address defaultAddress;
		private List<AddressResponse> addresses;

		public static Response of(User user) {


			return Response.builder()
					.defaultAddress(Objects.isNull(user.getDefaultAddress()) ? null : user.getDefaultAddress().getAddress())
					.addresses(Objects.isNull(user.getUserAddresses()) ? null : user.getUserAddresses().stream()
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
					.address(Objects.isNull(userAddress.getAddress()) ? "" : userAddress
							.getAddress().getAddress())
					.addressDetail(Objects.isNull(userAddress.getAddress()) ? "" : userAddress.getAddress().getAddressDetail())
					.build();
		}

	}

}
