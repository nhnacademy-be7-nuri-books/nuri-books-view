package shop.nuribooks.view.admin.coupon.categorycoupon.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import shop.nuribooks.view.admin.coupon.categorycoupon.dto.CategoryCouponRequest;
import shop.nuribooks.view.admin.coupon.categorycoupon.dto.CategoryCouponResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

@FeignClient(name = "categoryCoupon", url = "http://localhost:8080")

public interface CategoryCouponServiceClient {
	@GetMapping("/api/coupons/category-coupons/{category-id}")
	ResponseEntity<CategoryCouponResponse> getCategoryCoupon(@PathVariable("category-id") Long id);

	@PostMapping("/api/coupons/category-coupons")
	ResponseEntity<ResponseMessage> registerCategoryCoupon(@Valid @RequestBody CategoryCouponRequest couponRequest);

}
