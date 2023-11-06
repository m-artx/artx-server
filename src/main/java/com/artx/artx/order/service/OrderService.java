package com.artx.artx.order.service;

import com.artx.artx.common.error.ErrorCode;
import com.artx.artx.common.exception.BusinessException;
import com.artx.artx.order.entity.Delivery;
import com.artx.artx.order.entity.Order;
import com.artx.artx.order.model.CreateOrder;
import com.artx.artx.order.repository.OrderRepository;
import com.artx.artx.order.type.OrderStatus;
import com.artx.artx.payment.model.CreatePayment;
import com.artx.artx.payment.service.PaymentService;
import com.artx.artx.product.entity.Product;
import com.artx.artx.product.service.ProductService;
import com.artx.artx.user.entity.User;
import com.artx.artx.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final UserService userService;
	private final ProductService productService;
	private final PaymentService paymentService;

	@Transactional
	public CreatePayment.ReadyResponse createOrder(CreateOrder.Request request) {
		User user = userService.getUserByUserId(request.getUserId());
		Map<Long, Long> productIdsAndQuantities = extractProductIdsAndQuantities(request);
		List<Product> products = productService.getAllProductByIds(productIdsAndQuantities.keySet());

		//재고 확인
		products.stream().forEach(product -> {
			if(product.getQuantity() < productIdsAndQuantities.get(product.getId())){
				throw new BusinessException(ErrorCode.NOT_ENOUGH_QUANTITY);
			}
		});

		String orderTitle = extractOrderTitle(products);
		Order order = orderRepository.save(
				Order.builder()
						.title(orderTitle)
						.status(OrderStatus.ORDERED)
						.user(user)
						.delivery(Delivery.from(request))
						.totalAmount(products.stream().mapToLong(Product::getPrice).sum())
						.build()
		);



		try{
			products.stream().forEach(product -> {
				//재고 수량 감소 여부 확인
				boolean canDecrease = product.canDecrease(productIdsAndQuantities.get(product.getId()));
			});
			CreatePayment.ReadyResponse readyResponse = paymentService.readyPayment(order);
			return readyResponse;

		}catch (Exception e){
			e.printStackTrace();
			throw e;
		}


		//TODO: 결제 완료 후에 실행되게 미루기
//		products.stream().forEach(product -> {
//			//주문 연관 관계
//			order.addOrderProduct(OrderProduct.from(order, product, productIdsAndQuantities.get(product.getId())));
//
//			//재고 수량 감소
//			product.decrease(productIdsAndQuantities.get(product.getId()));
//		});

	}

	private String extractOrderTitle(List<Product> products) {
		String representativeProductName = products.get(0).getTitle();
		Integer orderProductsSize = products.size();

		String orderTitle = orderProductsSize > 0 ?
				representativeProductName + " 외 " + (orderProductsSize - 1) +"개의 작품" :
				representativeProductName;
		return orderTitle;
	}

	private Map<Long, Long> extractProductIdsAndQuantities(CreateOrder.Request request) {
		return request.getOrderDetails().stream()
				.collect(Collectors.toMap(CreateOrder.OrderDetail::getProductId, CreateOrder.OrderDetail::getProductQuantity));
	}

}
