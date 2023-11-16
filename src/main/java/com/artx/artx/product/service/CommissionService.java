//package com.artx.artx.product.service;
//
//import com.artx.artx.product.entity.Commission;
//import com.artx.artx.product.entity.CommissionId;
//import com.artx.artx.product.entity.Product;
//import com.artx.artx.product.model.commission.CreateCommission;
//import com.artx.artx.product.model.commission.ReadCommission;
//import com.artx.artx.product.repository.CommissionRepository;
//import com.artx.artx.product.type.CommissionStatus;
//import com.artx.artx.user.entity.User;
//import com.artx.artx.user.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class CommissionService {
//
//	private final UserService userService;
//	private final ProductService productService;
//	private final CommissionRepository commissionRepository;
//
//	@Transactional(readOnly = true)
//	public CreateCommission.Response createCommission(Long productId, CreateCommission.Request request) {
//		User user = userService.getUserByUserId(request.getUserId());
//		Product product = productService.getProductById(productId);
//
//		Commission commission = commissionRepository.save(Commission.builder()
//				.id(
//						CommissionId.builder()
//								.productId(product.getId())
//								.userId(user.getUserId())
//								.build()
//				)
//				.product(product)
//				.user(user)
//				.content(request.getContent())
//				.status(CommissionStatus.PROPOSAL)
//				.build()
//		);
//
//		return CreateCommission.Response.from(commission);
//	}
//
//
//	public Page<ReadCommission.ResponseAll> readAllCommissions(UUID userId, Pageable pageable) {
//		commissionRepository.findAllCommissionsByUserIdWithUserAndProduct(userId, pageable)
//				.map();
//
//	}
//}
