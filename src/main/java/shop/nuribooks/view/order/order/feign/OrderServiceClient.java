package shop.nuribooks.view.order.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import shop.nuribooks.view.common.dto.ResponseMessage;
import shop.nuribooks.view.order.order.dto.OrderCancelDto;
import shop.nuribooks.view.order.order.dto.request.OrderListPeriodRequest;
import shop.nuribooks.view.order.order.dto.request.OrderRegisterRequest;
import shop.nuribooks.view.order.order.dto.response.OrderDetailResponse;
import shop.nuribooks.view.order.order.dto.response.OrderInformationResponse;
import shop.nuribooks.view.order.order.dto.response.OrderListResponse;
import shop.nuribooks.view.order.order.dto.response.OrderRegisterResponse;
import shop.nuribooks.view.payment.dto.PaymentRequest;

@FeignClient(name = "order", url = "http://localhost:8080")
public interface OrderServiceClient {

	@GetMapping("/api/orders/{book-id}")
	ResponseEntity<OrderInformationResponse> getOrderInformation(@PathVariable("book-id") Long bookId,
		@RequestParam("quantity") Integer quantity);

	@GetMapping("/api/orders/cart/{cart-id}")
	ResponseEntity<OrderInformationResponse> getCartOrderInformation(@PathVariable("cart-id") String cartId);

	@PostMapping("/api/orders")
	ResponseEntity<OrderRegisterResponse> saveOrder(@RequestBody OrderRegisterRequest orderTempRegisterRequest);

	@GetMapping("/api/orders")
	ResponseEntity<Page<OrderListResponse>> getOrderList(
		@SpringQueryMap OrderListPeriodRequest orderListPeriodRequest,
		@RequestParam boolean includeOrdersInPendingStatus,
		Pageable pageable);

	@GetMapping("/api/orders/non-member/{customer-id}")
	ResponseEntity<Page<OrderListResponse>> getNonMemberOrderList(
		@SpringQueryMap OrderListPeriodRequest orderListPeriodRequest,
		@RequestParam boolean includeOrdersInPendingStatus,
		Pageable pageable,
		@PathVariable("customer-id") Long customerId);

	@GetMapping("/api/orders/details/{order-id}")
	ResponseEntity<OrderDetailResponse> getOrderDetail(@PathVariable("order-id") Long orderId,
		Pageable pageable);

	@PostMapping("/api/orders/verify")
	ResponseEntity<ResponseMessage> verifyOrderInformation(@RequestBody PaymentRequest paymentRequest);

	@GetMapping("/api/orders/cancel")
	ResponseEntity<Page<OrderListResponse>> getCancelledOrderList(
		@SpringQueryMap OrderListPeriodRequest orderListPeriodRequest,
		Pageable pageable);

	@GetMapping("/api/orders/cancel/{order-id}")
	ResponseEntity<OrderCancelDto> getOrderCancel(@PathVariable("order-id") Long orderId);
}
