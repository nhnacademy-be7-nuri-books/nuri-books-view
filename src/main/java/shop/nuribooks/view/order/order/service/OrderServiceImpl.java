package shop.nuribooks.view.order.order.service;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.common.util.ExceptionUtil;
import shop.nuribooks.view.exception.ApiErrorException;
import shop.nuribooks.view.exception.DefaultServerError;
import shop.nuribooks.view.order.order.dto.request.OrderListPeriodRequest;
import shop.nuribooks.view.order.order.dto.request.OrderTempRegisterRequest;
import shop.nuribooks.view.order.order.dto.response.OrderDetailResponse;
import shop.nuribooks.view.order.order.dto.response.OrderInformationResponse;
import shop.nuribooks.view.order.order.dto.response.OrderListResponse;
import shop.nuribooks.view.order.order.dto.response.OrderTempRegisterResponse;
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
			String message = ExceptionUtil.handleFeignException(e);
			log.error("saveOrder - 주문 폼 저장 실패 - {}", message);
			throw new ApiErrorException(e.status(), message);
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

	@Override
	public Page<OrderListResponse> getOrderList(OrderListPeriodRequest orderListPeriodRequest,
		boolean includeOrdersInPendingStatus, Pageable pageable) throws IOException {
		return orderServiceClient.getOrderList(orderListPeriodRequest, includeOrdersInPendingStatus, pageable)
			.getBody();
	}

	@Override
	public OrderDetailResponse getOrderDetail(Long orderId) {
		return orderServiceClient.getOrderDetail(orderId)
			.getBody();
	}

}
