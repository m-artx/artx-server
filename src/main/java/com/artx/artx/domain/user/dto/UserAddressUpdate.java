package com.artx.artx.domain.user.dto;

import lombok.Getter;

public class UserAddressUpdate {

	@Getter
	public static class Request {
		private Long addressId;
	}

}
