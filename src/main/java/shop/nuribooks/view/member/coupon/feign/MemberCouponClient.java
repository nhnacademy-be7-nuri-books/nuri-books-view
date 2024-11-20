package shop.nuribooks.view.member.coupon.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import shop.nuribooks.view.member.coupon.dto.CouponHistoryPeriodRequest;
import shop.nuribooks.view.member.coupon.dto.MemberCouponResponse;

@FeignClient(name = "member-coupon-client", url = "http://localhost:8080")
@RequestMapping("/api/member-coupons")
public interface MemberCouponClient {

	@GetMapping("/available")
	Page<MemberCouponResponse> getAvailableCouponsByMemberId(
		Pageable pageable,
		@SpringQueryMap CouponHistoryPeriodRequest couponHistoryPeriodRequest
	);

	@GetMapping("/expired-or-used")
	Page<MemberCouponResponse> getExpiredOrUsedCouponsByMemberId(
		Pageable pageable,
		@SpringQueryMap CouponHistoryPeriodRequest couponHistoryPeriodRequest
	);
}
