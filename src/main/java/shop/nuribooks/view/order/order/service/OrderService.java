package shop.nuribooks.view.order.order.service;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shop.nuribooks.view.order.order.dto.OrderInformationResponse;
import shop.nuribooks.view.order.order.dto.OrderListPeriodRequest;
import shop.nuribooks.view.order.order.dto.OrderListResponse;
import shop.nuribooks.view.order.order.dto.OrderTempRegisterRequest;
import shop.nuribooks.view.order.order.dto.OrderTempRegisterResponse;

public interface OrderService {

	OrderInformationResponse getOrderInformation(Long bookId, Integer quantity);

	OrderTempRegisterResponse saveOrder(OrderTempRegisterRequest orderTempRegisterRequest);

	OrderInformationResponse getCartOrderInformation(String cartId);

	Page<OrderListResponse> getOrderList(OrderListPeriodRequest orderListPeriodRequest,
		boolean includeOrdersInPendingStatus, Pageable pageable) throws IOException;
}
