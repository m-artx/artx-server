package com.artx.artx.cart.service;

import com.artx.artx.cart.entity.Cart;
import com.artx.artx.cart.entity.CartProduct;
import com.artx.artx.cart.entity.CartProductId;
import com.artx.artx.cart.model.CreateCartProduct;
import com.artx.artx.cart.model.DeleteCartProduct;
import com.artx.artx.cart.model.ReadCartProduct;
import com.artx.artx.cart.repository.CartProductRepository;
import com.artx.artx.cart.repository.CartRepository;
import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.order.model.CreateOrder;
import com.artx.artx.order.model.OrderProductIdAndQuantity;
import com.artx.artx.order.service.OrderService;
import com.artx.artx.payment.model.CreatePayment;
import com.artx.artx.product.entity.Product;
import com.artx.artx.product.entity.ProductStock;
import com.artx.artx.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

	private final CartProductRepository cartProductRepository;
	private final CartRepository cartRepository;
	private final OrderService orderService;
	private final ProductService productService;

	@Value(value = "${api.images}")
	private String imagesApiAddress;

	@Transactional
	public CreateCartProduct.Response addProduct(Long cartId, Long productId) {

		Cart cart = getCartById(cartId);
		Product product = productService.getProductById(productId);

		CartProduct cartProduct = CartProduct.from(cart, product);
		boolean hasCartProduct = cart.getCartProducts().contains(cartProduct);

		if (hasCartProduct) {
			throw new BusinessException(ErrorCode.DUPLICATED_CARTITEM);
		}

		cartProductRepository.save(cartProduct);
		cart.addCartProduct(cartProduct);

		return CreateCartProduct.Response.from(cartProduct);
	}

	@Transactional
	public void increaseProductQuantity(Long cartId, Long productId) {
		CartProduct cartProduct = getCartProductByCartIdAndProductId(cartId, productId);
		cartProduct.increase();
	}

	@Transactional
	public void decreaseProductQuantity(Long cartId, Long productId) {
		CartProduct cartProduct = getCartProductByCartIdAndProductId(cartId, productId);
		cartProduct.decrease();
		if (cartProduct.isEmpty()) {
			cartProductRepository.delete(cartProduct);
		}
	}

	@Transactional
	public CreatePayment.ReadyResponse createOrder(Long cartId, CreateOrder.Request request) {
		CreatePayment.ReadyResponse response = orderService.createOrder(request);
		cartProductRepository.deleteAllByCartIdAndProductIds(
				cartId,
				request.getOrderDetails().stream()
						.map(OrderProductIdAndQuantity::getProductId)
						.collect(Collectors.toList()));

		return response;
	}

	@Transactional(readOnly = true)
	public ReadCartProduct.Response readAllCarProductsByCartId(Long cartId, Pageable pageable) {
		Page<CartProduct> cartProducts = cartProductRepository.findAllByCart_Id(cartId, pageable);
		List<ProductStock> productStocks = cartProducts.stream().map(CartProduct::getProduct).map(Product::getProductStock).collect(Collectors.toList());
		Map<Long, ProductStock> productIdsAndQuantities = productStocks.stream()
				.collect(Collectors.toMap(productStock -> productStock.getProduct().getId(), productStock -> productStock));

		return ReadCartProduct.Response.from(
				cartId,
				cartProducts.map(cartProduct -> ReadCartProduct.CartProductDetail.from(
						imagesApiAddress,
						cartProduct,
						productIdsAndQuantities.get(cartProduct.getProduct().getId()).getQuantity())
				)
		);
	}

	private Cart getCartById(Long cartId) {
		return cartRepository.readCartWithCartProductAndProductByCartId(cartId).orElseThrow(() -> new BusinessException(ErrorCode.CART_NOT_FOUND));
	}

	private CartProduct getCartProductByCartIdAndProductId(Long cartId, Long productId) {
		return cartProductRepository.findById(CartProductId.from(cartId, productId)).orElseThrow(() -> new BusinessException(ErrorCode.CART_ITEM_NOT_FOUND));
	}

	@Transactional
	public void deleteAllCarProducts(Long cartId) {
		Cart cart = getCartById(cartId);
		cartProductRepository.deleteAllInBatch(cart.getCartProducts());
	}

	@Transactional
	public void deleteSelectedCartProducts(Long cartId, DeleteCartProduct.Request request) {
		List<Long> productIds = request.getProductIds();
		cartProductRepository.deleteSelectedCartProductsByCartIdAndProductIds(cartId, productIds);
	}
}
