package com.artx.artx.common.user.entity;

import com.artx.artx.common.user.type.UserRole;
import com.artx.artx.customer.cart.entity.Cart;
import com.artx.artx.etc.error.ErrorCode;
import com.artx.artx.etc.exception.BusinessException;
import com.artx.artx.etc.model.Address;
import com.artx.artx.etc.model.BaseEntity;
import com.artx.artx.common.user.model.UserCreate;
import com.artx.artx.common.user.type.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "Users")
public class User extends BaseEntity {

	@Id
	@UuidGenerator
	private UUID userId;

	@Enumerated(EnumType.STRING)
	private UserRole userRole;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private boolean isEmailYn;

	@Column(nullable = false)
	private boolean isDeleted;

	@Column(nullable = false)
	private String nickname;

	@Column(nullable = false)
	private String phoneNumber;

	private String profileImage;
	private String introduction;

	@Enumerated(EnumType.STRING)
	private UserStatus userStatus;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "cart_id", unique = true)
	private Cart cart;

	@Embedded
	@Column(nullable = false)
	private Address address;

	public boolean isArtist(){
		if(this.userRole != UserRole.ARTIST){
			throw new BusinessException(ErrorCode.CREATE_PRODUCT_ONLY_ARTIST);
		}

		return true;
	}

	public static User from(UserCreate.Request request, String password){
		return User.builder()
				.userStatus(UserStatus.INACTIVE)
				.userRole(UserRole.USER)
				.username(request.getUsername())
				.password(password)
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

	public void setProfileImage(String image){
		this.profileImage = image;
	}

	public void setDeleted(boolean deleted) {
		isDeleted = deleted;
	}

	public void setUserRole(UserRole previousRole) {
		this.userRole = previousRole;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
}
