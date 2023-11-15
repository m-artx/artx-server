package com.artx.artx.user.entity;

import com.artx.artx.cart.entity.Cart;
import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.common.model.Address;
import com.artx.artx.common.model.BaseEntity;
import com.artx.artx.user.model.CreateUser;
import com.artx.artx.user.type.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Users")
public class User extends BaseEntity {

	@Id
	@UuidGenerator
	private UUID userId;

	@Enumerated(EnumType.STRING)
	private UserRole userRole;
	private String username;
	private String password;
	private String email;
	private Boolean isEmailYn;
	private String nickname;
	private String phoneNumber;
	private String profileImage;
	private String introduction;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "cart_id")
	private Cart cart;

	@Embedded
	private Address address;

	public boolean canCreateProduct(){
		if(this.userRole != UserRole.ARTIST){
			throw new BusinessException(ErrorCode.CREATE_PRODUCT_ONLY_ARTIST);
		}

		return true;
	}


	public static User from(CreateUser.Request request){
		return User.builder()
				.userRole(request.getUserRole())
				.username(request.getUsername())
				.email(request.getEmail())
				.nickname(request.getNickname())
				.phoneNumber(request.getPhoneNumber())
				.address(Address.builder()
						.address(request.getAddress())
						.addressDetail(request.getAddressDetail())
						.build())
				.isEmailYn(request.getIsEmailYn())
				.cart(Cart.builder().build())
				.build();
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
