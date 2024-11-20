package shop.nuribooks.view.payment.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import shop.nuribooks.view.common.dto.ResponseMessage;
import shop.nuribooks.view.payment.dto.PaymentSuccessRequest;

@FeignClient(name = "payment", url = "http://localhost:8080")
public interface PaymentServiceClient {

	@PostMapping("/api/payments")
	ResponseEntity<ResponseMessage> donePayment(@RequestBody PaymentSuccessRequest paymentSuccessRequest);
}
