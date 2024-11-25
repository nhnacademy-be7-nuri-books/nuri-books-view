package shop.nuribooks.view.order.refund.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import shop.nuribooks.view.order.refund.dto.request.RefundRequest;
import shop.nuribooks.view.order.refund.dto.response.RefundInfoResponse;

@FeignClient(name = "refund", url = "http://localhost:8080")
public interface RefundServiceClient {

	@GetMapping("/api/orders/refunds/{order-id}")
	ResponseEntity<RefundInfoResponse> getRefundInfoResponse(@PathVariable(name = "order-id") Long orderId);

	@PostMapping("/api/orders/refunds/{order-id}")
	ResponseEntity<Void> registerRefund(@PathVariable(name = "order-id") Long orderId,
		@RequestBody RefundRequest refundRequest);
}
