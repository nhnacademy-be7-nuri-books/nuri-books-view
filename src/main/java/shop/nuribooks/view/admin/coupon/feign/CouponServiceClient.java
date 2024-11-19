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
import shop.nuribooks.view.admin.coupon.dto.CouponRequest;
import shop.nuribooks.view.admin.coupon.dto.CouponResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

@FeignClient(name = "coupon", url = "http://localhost:8080")
public interface CouponServiceClient {
	@GetMapping("/api/coupons")
	ResponseEntity<Page<CouponResponse>> getCoupons(Pageable pageable);

	@PostMapping("/api/coupons")
	ResponseEntity<ResponseMessage> registerCoupon(@Valid @RequestBody CouponRequest couponRequest);

	@PutMapping("/api/coupons/{coupon-id}")
	ResponseEntity<ResponseMessage> updateCoupon(@PathVariable("coupon-id") Long id, @Valid @RequestBody CouponRequest couponRequest);

	@DeleteMapping("/api/coupons/{coupon-id}")
	ResponseEntity<ResponseMessage> deleteCoupon(@PathVariable("coupon-id") Long id);
}