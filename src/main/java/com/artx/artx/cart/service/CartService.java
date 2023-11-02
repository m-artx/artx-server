package com.artx.artx.cart.service;

import com.artx.artx.cart.dto.CartResponse;
import com.artx.artx.cart.model.Cart;
import com.artx.artx.cart.model.CartItem;
import com.artx.artx.cart.model.CartItemId;
import com.artx.artx.cart.repository.CartItemRepository;
import com.artx.artx.cart.repository.CartRepository;
import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.common.model.CommonOrder;
import com.artx.artx.order.dto.OrderRequest;
import com.artx.artx.order.service.OrderService;
import com.artx.artx.product.entity.Product;
import com.artx.artx.product.repository.ProductRepository;
import com.artx.artx.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final CartItemRepository cartItemRepository;
	private final CartRepository cartRepository;
	private final OrderService orderService;

	public CartResponse.Create addItem(Long cartId, Long productId) {
		Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new BusinessException(ErrorCode.CART_NOT_FOUND));
		Product product = productRepository.findById(productId).orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

		CartItem cartItem = CartItem.builder().cart(cart).product(product).build();
		boolean isContainCartItem = cart.getCartItems().contains(cartItem);

		if (isContainCartItem) {
			throw new BusinessException(ErrorCode.DUPLICATED_CARTITEM);
		}

		CartItem item = cartItemRepository.save(CartItem.builder()
				.cartItemId(CartItemId.builder()
						.cartId(cart.getId())
						.productId(product.getId())
						.build()
				)
				.cart(cart)
				.product(product)
				.quantity(1L)
				.build()
		);

		cart.addCartItem(item);

		return CartResponse.Create.builder()
				.productId(cartItem.getProduct().getId())
				.savedDate(cartItem.getCreatedAt())
				.build();
	}

	@Transactional
	public void increaseQuantity(Long cartId, Long itemId) {
		CartItem cartItem = cartItemRepository.findById(CartItemId.builder().cartId(cartId).productId(itemId).build()).orElseThrow(() -> new BusinessException(ErrorCode.CART_ITEM_NOT_FOUND));
		cartItem.increase();
	}

	@Transactional
	public void decreaseQuantity(Long cartId, Long itemId) {
		CartItem cartItem = cartItemRepository.findById(CartItemId.builder().cartId(cartId).productId(itemId).build()).orElseThrow(() -> new BusinessException(ErrorCode.CART_ITEM_NOT_FOUND));
		cartItem.decrease();
		if(cartItem.isEmpty()){
			cartItemRepository.delete(cartItem);
		}
	}

	@Transactional
	public void orderByCart(Long cartId, CommonOrder.Create request){
		orderService.createOrder(request);
		cartItemRepository.deleteAllByCartIdAndProductIds(cartId, request.getOrderData().stream().map(OrderRequest.OrderData::getProductId).collect(Collectors.toList()));
	}
}
