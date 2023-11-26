package com.artx.artx.user.entity;

import com.artx.artx.cart.entity.Cart;
import com.artx.artx.user.model.UserCreate;
import com.artx.artx.user.type.UserRole;
import com.artx.artx.user.type.UserStatus;
import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.common.model.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
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

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", nullable = false)
	private UserAddress defaultAddress;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<UserAddress> userAddresses;

	public boolean isArtist() {
		if (this.userRole != UserRole.ARTIST) {
			throw new BusinessException(ErrorCode.CREATE_PRODUCT_ONLY_ARTIST);
		}

		return true;
	}

	public static User from(UserCreate.Request request, String password) {
		return User.builder()
				.userStatus(UserStatus.INACTIVE)
				.userRole(UserRole.USER)
				.username(request.getUsername())
				.password(password)
				.email(request.getEmail())
				.nickname(request.getNickname())
				.phoneNumber(request.getPhoneNumber())
				.isEmailYn(request.getIsEmailYn())
				.cart(Cart.builder().build())
				.defaultAddress(UserAddress.from(request))
				.build();
	}

	public void addAddress(UserAddress userAddress) {
		if (this.userAddresses == null) {
			this.userAddresses = new ArrayList<>();
		}
		userAddress.setUser(this);
		this.userAddresses.add(userAddress);
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setProfileImage(String image) {
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

	public void setDefaultAddress(UserAddress userAddress) {
		this.defaultAddress = userAddress;
	}
}
