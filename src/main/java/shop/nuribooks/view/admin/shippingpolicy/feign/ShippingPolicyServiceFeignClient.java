package shop.nuribooks.view.admin.shippingpolicy.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import shop.nuribooks.view.admin.shippingpolicy.dto.ShippingPolicyRequest;
import shop.nuribooks.view.admin.shippingpolicy.dto.ShippingPolicyResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

@FeignClient(name = "shipping-policy", url = "http://localhost:8080/admin/api/shipping-policies")
public interface ShippingPolicyServiceFeignClient {
	@GetMapping
	ResponseEntity<Page<ShippingPolicyResponse>> getShippingPolicies(Pageable pageable);

	@PostMapping
	ResponseEntity<ResponseMessage> registerShippingPolicy(
		@Valid @RequestBody ShippingPolicyRequest shippingPolicyRequest);

	@PutMapping("/{shipping_policy_id}")
	ResponseEntity<ResponseMessage> updateShippingPolicy(
		@PathVariable("shipping_policy_id") Long id,
		@Valid @RequestBody ShippingPolicyRequest shippingPolicyRequest);

	@PutMapping("/{shipping_policy_id}/expire")
	ResponseEntity<ResponseMessage> expireShippingPolicy(
		@PathVariable("shipping_policy_id") Long id);
}
