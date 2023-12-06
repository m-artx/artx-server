package com.artx.artx.domain.cart.application;

import com.artx.artx.domain.cart.dao.CartProductRepository;
import com.artx.artx.domain.cart.domain.Cart;
import com.artx.artx.domain.cart.domain.CartProduct;
import com.artx.artx.domain.cart.domain.CartProductId;
import com.artx.artx.domain.cart.dto.CartProductCreate;
import com.artx.artx.domain.cart.dto.CartProductDelete;
import com.artx.artx.domain.cart.dto.CartProductRead;
import com.artx.artx.domain.order.dto.OrderCreate;
import com.artx.artx.global.common.error.ErrorCode;
import com.artx.artx.global.common.error.exception.BusinessException;
import com.artx.artx.domain.order.dto.OrderProductIdAndQuantity;
import com.artx.artx.domain.order.application.OrderService;
import com.artx.artx.domain.payment.dto.PaymentCreate;
import com.artx.artx.domain.product.domain.Product;
import com.artx.artx.domain.product.domain.ProductStock;
import com.artx.artx.domain.product.application.ProductService;
import com.artx.artx.domain.user.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

	private final CartProductRepository cartProductRepository;
	private final OrderService orderService;
	private final ProductService productService;
	private final UserService userService;

	@Value(value = "${api.images}")
	private String imagesApiAddress;

	@Transactional
	public CartProductCreate.Response addProduct(UUID userId, Long productId) {
		Cart cart = getUserWithCartById(userId);
		Product product = productService.getProductById(productId);

		CartProduct cartProduct = CartProduct.from(cart, product);
		boolean hasCartProduct = cart.getCartProducts().contains(cartProduct);

		if (hasCartProduct) {
			throw new BusinessException(ErrorCode.DUPLICATED_CARTITEM);
		}

		cartProductRepository.save(cartProduct);
		cart.addCartProduct(cartProduct);

		return CartProductCreate.Response.of(cartProduct);
	}

	@Transactional
	public void increaseProductQuantity(UUID userId, Long productId) {
		Cart cart = getUserWithCartById(userId);
		CartProduct cartProduct = getCartProductByCartIdAndProductId(cart.getId(), productId);
		cartProduct.increase();
	}

	@Transactional
	public void decreaseProductQuantity(UUID userId, Long productId) {
		Cart cart = getUserWithCartById(userId);
		CartProduct cartProduct = getCartProductByCartIdAndProductId(cart.getId(), productId);
		cartProduct.decrease();
		if (cartProduct.isEmpty()) {
			cartProductRepository.delete(cartProduct);
		}
	}

	@Transactional
	public PaymentCreate.Response orderSellectedCartProducts(UUID userId, OrderCreate.Request request) {
		PaymentCreate.Response response = orderService.createOrder(userId, request);
		Cart cart = getUserWithCartById(userId);

		cartProductRepository.deleteAllByCartIdAndProductIds(
				cart.getId(),
				request.getOrderProductDetails().stream()
						.map(OrderProductIdAndQuantity::getProductId)
						.collect(Collectors.toList()));

		return response;
	}

	@Transactional(readOnly = true)
	public CartProductRead.Response readAllCarProductsByCartId(UUID userId, Pageable pageable) {
		Cart cart = getUserWithCartById(userId);

		Page<CartProduct> cartProducts = cartProductRepository.findAllByCart_Id(cart.getId(), pageable);
		List<ProductStock> productStocks = cartProducts.stream().map(CartProduct::getProduct).map(Product::getProductStock).collect(Collectors.toList());
		Map<Long, ProductStock> productIdsAndQuantities = productStocks.stream()
				.collect(Collectors.toMap(productStock -> productStock.getProduct().getId(), productStock -> productStock));

		return CartProductRead.Response.from(
				cart.getId(),
				cartProducts.map(cartProduct -> CartProductRead.CartProductDetail.of(
						imagesApiAddress,
						cartProduct,
						productIdsAndQuantities.get(cartProduct.getProduct().getId()).getQuantity())
				)
		);
	}

	private Cart getUserWithCartById(UUID userId) {
		return userService.getUserWithCartById(userId).getCart();
	}

	private CartProduct getCartProductByCartIdAndProductId(Long cartId, Long productId) {
		return cartProductRepository.findById(CartProductId.from(cartId, productId)).orElseThrow(() -> new BusinessException(ErrorCode.CART_ITEM_NOT_FOUND));
	}

	@Transactional
	public void deleteAllCartProducts(UUID userId) {
		Cart cart = getUserWithCartById(userId);
		cartProductRepository.deleteAllInBatch(cart.getCartProducts());
	}

	@Transactional
	public void deleteSelectedCartProducts(UUID userId, CartProductDelete.Request request) {
		Cart cart = getUserWithCartById(userId);

		List<Long> productIds = request.getProductIds();
		cartProductRepository.deleteSelectedCartProductsByCartIdAndProductIds(cart.getId(), productIds);
	}

}
