package com.artx.artx.cart.service;

import com.artx.artx.cart.dto.CreateCartItem;
import com.artx.artx.cart.dto.DeleteCartItem;
import com.artx.artx.cart.dto.ReadCartItem;
import com.artx.artx.cart.model.Cart;
import com.artx.artx.cart.model.CartItem;
import com.artx.artx.cart.model.CartItemId;
import com.artx.artx.cart.repository.CartItemRepository;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

	private final CartItemRepository cartItemRepository;
	private final CartRepository cartRepository;
	private final OrderService orderService;
	private final ProductService productService;

	@Value(value = "${api.images}")
	private String imagesApiAddress;

	@Transactional
	public CreateCartItem.Response addProduct(Long cartId, Long productId) {

		Cart cart = getCartById(cartId);
		Product product = productService.getProductById(productId);

		CartItem cartItem = CartItem.from(cart, product);
		boolean hasCartItem = cart.getCartItems().contains(cartItem);

		if (hasCartItem) {
			throw new BusinessException(ErrorCode.DUPLICATED_CARTITEM);
		}

		CartItem item = cartItemRepository.save(cartItem);
		cart.addCartItem(item);

		return CreateCartItem.Response.from(cartItem);
	}

	@Transactional
	public void increaseProductQuantity(Long cartId, Long productId) {
		CartItem cartItem = getCartItemByCartIdAndProductId(cartId, productId);
		cartItem.increase();
	}

	@Transactional
	public void decreaseProductQuantity(Long cartId, Long productId) {
		CartItem cartItem = getCartItemByCartIdAndProductId(cartId, productId);
		cartItem.decrease();
		if (cartItem.isEmpty()) {
			cartItemRepository.delete(cartItem);
		}
	}

	@Transactional
	public CreatePayment.ReadyResponse createOrder(Long cartId, CreateOrder.Request request) {
		CreatePayment.ReadyResponse response = orderService.createOrder(request);
		cartItemRepository.deleteAllByCartIdAndProductIds(
				cartId,
				request.getOrderDetails().stream()
						.map(OrderProductIdAndQuantity::getProductId)
						.collect(Collectors.toList()));

		return response;
	}

	@Transactional(readOnly = true)
	public ReadCartItem.Response readAllCarItemsByCartId(Long cartId, Pageable pageable) {
		Cart cart = getCartById(cartId);
		List<CartItem> cartItems = cart.getCartItems();
		List<ProductStock> productStocks = cartItems.stream().map(CartItem::getProduct).map(Product::getProductStock).collect(Collectors.toList());
		Map<Long, ProductStock> productIdsAndQuantities = productStocks.stream()
				.collect(Collectors.toMap(productStock -> productStock.getProduct().getId(), productStock -> productStock));

		Page<CartItem> cartItemPages = new PageImpl<>(cartItems, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), cartItems.size());

		return ReadCartItem.Response.from(
				cartId,
				cartItemPages.map(cartItem -> ReadCartItem.CartItemDetail.from(
						imagesApiAddress,
						cartItem,
						productIdsAndQuantities.get(cartItem.getProduct().getId()).getQuantity())
				)
		);
	}

	private Cart getCartById(Long cartId) {
		return cartRepository.readCartWithCartItemAndProductByCartId(cartId).orElseThrow(() -> new BusinessException(ErrorCode.CART_NOT_FOUND));
	}

	private CartItem getCartItemByCartIdAndProductId(Long cartId, Long productId) {
		return cartItemRepository.findById(CartItemId.from(cartId, productId)).orElseThrow(() -> new BusinessException(ErrorCode.CART_ITEM_NOT_FOUND));
	}

	@Transactional
	public void deleteAllCarItems(Long cartId) {
		Cart cart = getCartById(cartId);
		cartItemRepository.deleteAllInBatch(cart.getCartItems());
	}

	@Transactional
	public void deleteSelectedCartItems(Long cartId, DeleteCartItem.Request request) {
		List<Long> productIds = request.getProductIds();
		cartItemRepository.deleteSelectedCartItemsByCartIdAndProductIds(cartId, productIds);
	}
}
