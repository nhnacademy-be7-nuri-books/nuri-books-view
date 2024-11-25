package shop.nuribooks.view.order.returnrequest.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import shop.nuribooks.view.order.returnrequest.dto.request.ReturnRequestRegisterRequest;

@FeignClient(name = "return-request", url = "http://localhost:8080")
public interface ReturnRequestServiceClient {

	@PostMapping("/api/orders/return-requests/{order-id}")
	ResponseEntity<Void> registerReturnRequest(@PathVariable(name = "order-id") Long orderId,
		@RequestBody ReturnRequestRegisterRequest returnRequestRegisterRequest);

}
