package com.artx.artx.cart.service;

import com.artx.artx.cart.dto.CreateCartItem;
import com.artx.artx.cart.dto.ReadCartItem;
import com.artx.artx.cart.model.Cart;
import com.artx.artx.cart.model.CartItem;
import com.artx.artx.cart.model.CartItemId;
import com.artx.artx.cart.repository.CartItemRepository;
import com.artx.artx.cart.repository.CartRepository;
import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.order.model.CreateOrder;
import com.artx.artx.order.model.OrderDetail;
import com.artx.artx.order.service.OrderService;
import com.artx.artx.product.entity.Product;
import com.artx.artx.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
	public void createOrder(Long cartId, CreateOrder.Request request) {
		orderService.createOrder(request);
		cartItemRepository.deleteAllByCartIdAndProductIds(
				cartId,
				request.getOrderDetails().stream()
						.map(OrderDetail::getProductId)
						.collect(Collectors.toList()));
	}

	@Transactional(readOnly = true)
	public ReadCartItem.Response fetchCarItemsByCartId(Long cartId) {
		Cart cart = getCartById(cartId);
		List<CartItem> cartItems = cart.getCartItems();
		return ReadCartItem.Response.from(
				cartId,
				cartItems.stream()
						.map(cartItem -> ReadCartItem.CartItemDetail.from(imagesApiAddress, cartItem))
						.collect(Collectors.toList())
		);
	}

	//TODO: N+1
	private Cart getCartById(Long cartId) {
		return cartRepository.readCartWithCartItemAndProductByCartId(cartId).orElseThrow(() -> new BusinessException(ErrorCode.CART_NOT_FOUND));
	}

	private CartItem getCartItemByCartIdAndProductId(Long cartId, Long productId) {
		return cartItemRepository.findById(CartItemId.from(cartId, productId)).orElseThrow(() -> new BusinessException(ErrorCode.CART_ITEM_NOT_FOUND));
	}
}
