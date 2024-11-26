package shop.nuribooks.view.admin.coupon.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import shop.nuribooks.view.admin.coupon.dto.BookCouponRequest;
import shop.nuribooks.view.admin.coupon.dto.CategoryCouponRequest;
import shop.nuribooks.view.admin.coupon.dto.CouponRequest;
import shop.nuribooks.view.admin.coupon.dto.CouponResponse;
import shop.nuribooks.view.admin.coupon.dto.MemberCouponIssueRequest;
import shop.nuribooks.view.admin.coupon.enums.CouponType;
import shop.nuribooks.view.common.dto.ResponseMessage;

@FeignClient(name = "coupon", url = "http://localhost:8080")
public interface CouponServiceClient {
	@GetMapping("/api/coupons")
	ResponseEntity<Page<CouponResponse>> getCoupons(@RequestParam(value = "type") CouponType type, Pageable pageable);

	@GetMapping("/api/coupons/{coupon-id}")
	ResponseEntity<CouponResponse> getCoupon(@PathVariable("coupon-id") Long id);

	@PostMapping("/api/coupons")
	ResponseEntity<ResponseMessage> registerCoupon(@Valid @RequestBody CouponRequest couponRequest);

	@PostMapping("/api/coupons/book-coupons")
	ResponseEntity<ResponseMessage> registerBookCoupon(@Valid @RequestBody BookCouponRequest bookCouponRequest);

	@PutMapping("/api/coupons/{coupon-id}")
	ResponseEntity<ResponseMessage> updateCoupon(@PathVariable("coupon-id") Long id,
		@Valid @RequestBody CouponRequest couponRequest);

	@PutMapping("/api/coupons/{coupon-id}/expire")
	ResponseEntity<ResponseMessage> expireCoupon(@PathVariable("coupon-id") Long id);

	@PostMapping("/api/coupons/category-coupons")
	ResponseEntity<ResponseMessage> registerCategoryCoupon(@Valid @RequestBody CategoryCouponRequest couponRequest);

	@PostMapping("/api/member-coupons")
	ResponseMessage issueMemberCoupon(@RequestBody MemberCouponIssueRequest memberCouponIssueRequest);
}
