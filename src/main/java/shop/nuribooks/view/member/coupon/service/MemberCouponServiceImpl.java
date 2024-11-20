package shop.nuribooks.view.member.coupon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.member.coupon.dto.CouponHistoryPeriodRequest;
import shop.nuribooks.view.member.coupon.dto.MemberCouponResponse;
import shop.nuribooks.view.member.coupon.feign.MemberCouponClient;

@Service
@RequiredArgsConstructor
public class MemberCouponServiceImpl implements MemberCouponService {

	private final MemberCouponClient memberCouponClient;

	@Override
	public Page<MemberCouponResponse> getAvailableCouponsByMemberId(Pageable pageable,
		CouponHistoryPeriodRequest couponHistoryPeriodRequest) {
		return memberCouponClient.getAvailableCouponsByMemberId(pageable, couponHistoryPeriodRequest);
	}

	@Override
	public Page<MemberCouponResponse> getExpiredOrUsedCouponsByMemberId(Pageable pageable,
		CouponHistoryPeriodRequest couponHistoryPeriodRequest) {
		return memberCouponClient.getExpiredOrUsedCouponsByMemberId(pageable, couponHistoryPeriodRequest);
	}
}
