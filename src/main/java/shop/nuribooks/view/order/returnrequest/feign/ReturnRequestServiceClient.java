package shop.nuribooks.view.order.returnrequest.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import shop.nuribooks.view.order.returnrequest.dto.request.ReturnRequestRegisterRequest;
import shop.nuribooks.view.order.returnrequest.dto.response.ReturnRequestResponse;

@FeignClient(name = "return-request", url = "http://localhost:8080")
public interface ReturnRequestServiceClient {

	@GetMapping("/api/orders/return-requests")
	ResponseEntity<Page<ReturnRequestResponse>> getReturnRequests(Pageable pageable);

	@PostMapping("/api/orders/return-requests/{order-id}")
	ResponseEntity<Void> registerReturnRequest(@PathVariable(name = "order-id") Long orderId,
		@RequestBody ReturnRequestRegisterRequest returnRequestRegisterRequest);

}
