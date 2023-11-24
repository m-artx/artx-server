package com.artx.artx.common.user.service;

import com.artx.artx.common.image.service.ImageService;
import com.artx.artx.common.user.entity.User;
import com.artx.artx.common.user.entity.UserAddress;
import com.artx.artx.common.user.model.*;
import com.artx.artx.common.user.repository.UserAddressRepository;
import com.artx.artx.etc.error.ErrorCode;
import com.artx.artx.etc.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserMyPageService {

	private final PasswordEncoder passwordEncoder;
	private final ImageService imageService;
	private final UserService userService;
	private final UserAddressRepository userAddressRepository;

	@Value(value = "${api.images}")
	private String imagesApiAddress;

	@Transactional
	public void setProfileImage(UUID userId, MultipartFile file) {
		User user = userService.getUserByUserId(userId);
		String image = imageService.saveProfileImage(file);
		imageService.removeImage(user.getProfileImage());
		user.setProfileImage(image);
	}

	@Transactional
	public UserUpdate.Response updatePassword(UUID userId, UserUpdate.Request request) {
		User user = userService.getUserByUserId(userId);
		userService.isValidPassword(request.getPreviousPassword(), user.getPassword());
		user.setPassword(passwordEncoder.encode(request.getPresentPassword()));
		return UserUpdate.Response.builder().updatedAt(LocalDateTime.now()).build();
	}

	@Transactional
	public void addAddress(UUID userId, UserAddressCreate.Request request) {
		User user = userService.getUserByUserId(userId);
		if(user.getUserAddresses() != null && user.getUserAddresses().size() > 4){
			throw new BusinessException(ErrorCode.MAXIMUM_ADDITIONAL_ADDRESS_SIZE_IS_5);
		}
		UserAddress userAddress = userAddressRepository.save(UserAddress.from(request));
		user.addAddress(userAddress);
	}

	@Transactional(readOnly = true)
	public UserAddressRead.Response getAllAddress(UUID userId) {
		User user = userService.getUserWithAddressByUserId(userId);
		return UserAddressRead.Response.of(user);
	}

	@Transactional
	public void updateDefaultAddress(UUID userId, UserAddressUpdate.Request request) {
		if(!userAddressRepository.existsById(request.getAddressId())){
			throw new BusinessException(ErrorCode.ADDRESS_NOT_FOUND);
		}
		User user = userService.getUserWithAddressByUserId(userId);
		UserAddress userAddress = userAddressRepository.findById(request.getAddressId()).orElseThrow(() -> new BusinessException(ErrorCode.ADDRESS_NOT_FOUND));

		user.setDefaultAddress(userAddress);
	}

	@Transactional
	public void deleteAddress(UUID userId, UserAddressDelete.Request request) {
		UserAddress userAddress = userAddressRepository.findById(request.getAddressId()).orElseThrow(() -> new BusinessException(ErrorCode.ADDRESS_NOT_FOUND));
		User user = userService.getUserWithAddressByUserId(userId);

		if(userAddress.getUser() != user){
			throw new BusinessException(ErrorCode.CAN_DELETE_ONLY_OWNER);
		}

		if(user.getDefaultAddress().getId() == userAddress.getId()){
			throw new BusinessException(ErrorCode.CAN_DELETE_DEFAULT_ADDRESS);
		}

		userAddress.setUser(null);
		userAddressRepository.deleteById(request.getAddressId());
	}

//	public UserUpdate.Response addAddress(UUID userId, UserAddressCreate.Request request, Pageable pageable) {
//		additioanlAddressRepository.findAllByUser_UserId(userId, pageable).or
//		return null;
//	}
}
