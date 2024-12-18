package shop.nuribooks.view.admin.coupon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.coupon.dto.CouponRequest;
import shop.nuribooks.view.admin.coupon.dto.CouponResponse;
import shop.nuribooks.view.admin.coupon.dto.MemberCouponIssueRequest;
import shop.nuribooks.view.admin.coupon.enums.CouponType;
import shop.nuribooks.view.admin.coupon.feign.CouponServiceClient;
import shop.nuribooks.view.common.dto.ResponseMessage;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
	private final CouponServiceClient couponServiceClient;

	@Override
	public Page<CouponResponse> getCoupons(CouponType type, Pageable pageable) {
		return couponServiceClient.getCoupons(type, pageable).getBody();
	}

	@Override
	public Page<CouponResponse> getAllCoupons(Pageable pageable) {
		return couponServiceClient.getAllCoupons(pageable).getBody();
	}

	@Override
	public ResponseMessage registerCoupon(CouponRequest couponRequest) {
		return couponServiceClient.registerCoupon(couponRequest).getBody();
	}

	@Override
	public ResponseMessage updateCoupon(Long id, CouponRequest couponRequest) {
		return couponServiceClient.updateCoupon(id, couponRequest).getBody();
	}

	@Override
	public ResponseMessage expireCoupon(Long id) {
		return couponServiceClient.expireCoupon(id).getBody();
	}

	@Override
	public CouponResponse getCouponById(Long couponId) {
		return couponServiceClient.getCoupon(couponId).getBody();
	}

	@Override
	public ResponseMessage issueMemberCoupon(MemberCouponIssueRequest memberCouponIssueRequest) {
		return couponServiceClient.issueMemberCoupon(memberCouponIssueRequest);
	}

}
