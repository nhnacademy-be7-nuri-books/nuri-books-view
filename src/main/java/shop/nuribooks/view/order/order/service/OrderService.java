package shop.nuribooks.view.order.order.service;

import shop.nuribooks.view.order.order.dto.OrderInformationResponse;
import shop.nuribooks.view.order.order.dto.OrderTempRegisterRequest;
import shop.nuribooks.view.order.order.dto.OrderTempRegisterResponse;

public interface OrderService {

	OrderInformationResponse getOrderInformation(Long bookId, Integer quantity);

	OrderTempRegisterResponse saveOrder(OrderTempRegisterRequest orderTempRegisterRequest);
}
