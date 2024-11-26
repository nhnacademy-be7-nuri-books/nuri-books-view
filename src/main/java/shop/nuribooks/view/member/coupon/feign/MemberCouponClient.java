package shop.nuribooks.view.member.coupon.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import shop.nuribooks.view.member.coupon.dto.MemberCouponResponse;

@FeignClient(name = "member-coupon-client", url = "http://localhost:8080")
public interface MemberCouponClient {

	@GetMapping("/api/member-coupons/available")
	Page<MemberCouponResponse> getAvailableCouponsByMemberId(Pageable pageable);

	@GetMapping("/api/member-coupons/expired-or-used")
	Page<MemberCouponResponse> getExpiredOrUsedCouponsByMemberId(Pageable pageable);

	@PostMapping("/api/member-coupons/{coupon-id}")
	ResponseEntity<Void> issueMemberToBookCoupon(@PathVariable(name = "coupon-id") Long couponId);

	@PostMapping("/api/member-coupons/category/{coupon-id}")
	ResponseEntity<Void> issueMemberToCategoryCoupon(@PathVariable(name = "coupon-id") Long couponId);
}
