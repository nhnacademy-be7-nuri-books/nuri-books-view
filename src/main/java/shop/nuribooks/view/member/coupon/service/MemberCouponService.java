package shop.nuribooks.view.member.coupon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shop.nuribooks.view.member.coupon.dto.MemberCouponResponse;

public interface MemberCouponService {
	Page<MemberCouponResponse> getAvailableCouponsByMemberId(Pageable pageable);

	Page<MemberCouponResponse> getExpiredOrUsedCouponsByMemberId(Pageable pageable);

}
