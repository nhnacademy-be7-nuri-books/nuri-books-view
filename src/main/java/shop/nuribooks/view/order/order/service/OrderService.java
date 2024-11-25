package shop.nuribooks.view.order.order.service;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shop.nuribooks.view.order.order.dto.request.OrderListPeriodRequest;
import shop.nuribooks.view.order.order.dto.request.OrderRegisterRequest;
import shop.nuribooks.view.order.order.dto.response.OrderDetailResponse;
import shop.nuribooks.view.order.order.dto.response.OrderInformationResponse;
import shop.nuribooks.view.order.order.dto.response.OrderListResponse;
import shop.nuribooks.view.order.order.dto.response.OrderRegisterResponse;

public interface OrderService {

	OrderInformationResponse getOrderInformation(Long bookId, Integer quantity);

	OrderRegisterResponse saveOrder(OrderRegisterRequest orderTempRegisterRequest);

	OrderInformationResponse getCartOrderInformation(String cartId);

	Page<OrderListResponse> getOrderList(OrderListPeriodRequest orderListPeriodRequest,
		boolean includeOrdersInPendingStatus, Pageable pageable) throws IOException;

	OrderDetailResponse getOrderDetail(Long orderId, Pageable pageable);
}
