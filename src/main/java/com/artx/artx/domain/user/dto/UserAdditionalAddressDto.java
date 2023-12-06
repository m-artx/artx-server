package com.artx.artx.domain.user.dto;

import com.artx.artx.domain.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserAdditionalAddressDto {

	private Address additionalAddress;

}
