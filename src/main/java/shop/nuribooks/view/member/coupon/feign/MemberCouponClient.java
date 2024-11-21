package shop.nuribooks.view.member.coupon.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

import shop.nuribooks.view.member.coupon.dto.MemberCouponResponse;

@FeignClient(name = "member-coupon-client", url = "http://localhost:8080")
public interface MemberCouponClient {

	@GetMapping("/api/member-coupons/available")
	Page<MemberCouponResponse> getAvailableCouponsByMemberId(Pageable pageable);

	@GetMapping("/api/member-coupons/expired-or-used")
	Page<MemberCouponResponse> getExpiredOrUsedCouponsByMemberId(Pageable pageable);
}
