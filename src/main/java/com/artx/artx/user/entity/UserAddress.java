package com.artx.artx.user.entity;

import com.artx.artx.user.model.UserAddressCreate;
import com.artx.artx.user.model.UserCreate;
import com.artx.artx.common.model.Address;
import com.artx.artx.common.model.BaseEntity;
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
public class UserAddress extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Embedded
	private Address address;

	public static UserAddress from(UserAddressCreate.Request request) {
		return UserAddress.builder()
				.address(Address.builder()
						.address(request.getAddress())
						.addressDetail(request.getAddressDetail())
						.build()
				).build();
	}

	public static UserAddress from(UserCreate.Request request) {
		return UserAddress.builder()
				.address(Address.builder()
						.address(request.getAddress())
						.addressDetail(request.getAddressDetail())
						.build()
				).build();
	}

	public void setUser(User user) {
		this.user = user;
	}
}
