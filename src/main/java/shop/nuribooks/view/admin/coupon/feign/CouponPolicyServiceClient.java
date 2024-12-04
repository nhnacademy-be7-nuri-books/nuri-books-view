package shop.nuribooks.view.admin.coupon.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import shop.nuribooks.view.admin.coupon.dto.CouponPolicyRequest;
import shop.nuribooks.view.admin.coupon.dto.CouponPolicyResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

@FeignClient(name = "couponPolicy", url = "http://localhost:8080")

public interface CouponPolicyServiceClient {

	@GetMapping("/api/coupon-policies")
	ResponseEntity<Page<CouponPolicyResponse>> getCouponPolicies(Pageable pageable);

	@GetMapping("/api/coupon-policies/{coupon-policy-id}")
	ResponseEntity<CouponPolicyResponse> getCouponPolicy(@PathVariable("coupon-policy-id") Long id);

	@PostMapping("/api/coupon-policies")
	ResponseEntity<ResponseMessage> registerCouponPolicy(@Valid @RequestBody CouponPolicyRequest couponPolicyRequest);

	@PutMapping("/api/coupon-policies/{coupon-policy-id}")
	ResponseEntity<ResponseMessage> updateCouponPolicy(@PathVariable("coupon-policy-id") Long id,
		@Valid @RequestBody CouponPolicyRequest couponPolicyRequest);

	@DeleteMapping("/api/coupon-policies/{coupon-policy-id}")
	ResponseEntity<ResponseMessage> deleteCouponPolicy(@PathVariable("coupon-policy-id") Long id);
}
