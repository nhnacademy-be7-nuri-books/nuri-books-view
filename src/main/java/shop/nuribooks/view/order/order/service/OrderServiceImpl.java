package shop.nuribooks.view.order.order.service;

import org.springframework.stereotype.Service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.exception.DefaultServerError;
import shop.nuribooks.view.order.order.dto.OrderInformationResponse;
import shop.nuribooks.view.order.order.dto.OrderTempRegisterRequest;
import shop.nuribooks.view.order.order.dto.OrderTempRegisterResponse;
import shop.nuribooks.view.order.order.feign.OrderServiceClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

	private final OrderServiceClient orderServiceClient;

	@Override
	public OrderInformationResponse getOrderInformation(Long bookId, Integer quantity) {

		try {
			return orderServiceClient.getOrderInformation(bookId, quantity).getBody();
		} catch (FeignException e) {
			log.error("getOrderInformation - 주문 폼 불러오기 실패");
			throw new DefaultServerError(e.status(), e.getMessage());
		}

	}

	@Override
	public OrderTempRegisterResponse saveOrder(OrderTempRegisterRequest orderTempRegisterRequest) {
		try {
			return orderServiceClient.saveOrder(orderTempRegisterRequest).getBody();
		} catch (FeignException e) {
			log.error("saveOrder - 주문 폼 불러오기 실패");
			throw new DefaultServerError(e.status(), e.getMessage());
		}
	}

	@Override
	public OrderInformationResponse getCartOrderInformation(String cartId) {
		try {
			return orderServiceClient.getCartOrderInformation(cartId).getBody();
		} catch (FeignException e) {
			log.error("getOrderInformation - 주문 폼 불러오기 실패");
			throw new DefaultServerError(e.status(), e.getMessage());
		}
	}

}
