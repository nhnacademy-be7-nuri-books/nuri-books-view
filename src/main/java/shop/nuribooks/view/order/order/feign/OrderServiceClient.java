package shop.nuribooks.view.order.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import shop.nuribooks.view.order.order.dto.OrderInformationResponse;
import shop.nuribooks.view.order.order.dto.OrderTempRegisterRequest;
import shop.nuribooks.view.order.order.dto.OrderTempRegisterResponse;

@FeignClient(name = "order", url = "http://localhost:8080")
public interface OrderServiceClient {

	@GetMapping("/api/orders/{book-id}")
	ResponseEntity<OrderInformationResponse> getOrderInformation(@PathVariable("book-id") Long bookId,
		@RequestParam("quantity") Integer quantity);

	@GetMapping("/api/orders/cart/{cart-id}")
	ResponseEntity<OrderInformationResponse> getCartOrderInformation(@PathVariable("cart-id") String cartId);


	@PostMapping("/api/orders")
	ResponseEntity<OrderTempRegisterResponse> saveOrder(@RequestBody OrderTempRegisterRequest orderTempRegisterRequest);
}
