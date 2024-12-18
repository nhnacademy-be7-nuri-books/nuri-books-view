package shop.nuribooks.view.admin.coupon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shop.nuribooks.view.admin.coupon.dto.CouponRequest;
import shop.nuribooks.view.admin.coupon.dto.CouponResponse;
import shop.nuribooks.view.admin.coupon.dto.MemberCouponIssueRequest;
import shop.nuribooks.view.admin.coupon.enums.CouponType;
import shop.nuribooks.view.common.dto.ResponseMessage;

public interface CouponService {
	Page<CouponResponse> getCoupons(CouponType type, Pageable pageable);

	Page<CouponResponse> getAllCoupons(Pageable pageable);

	ResponseMessage registerCoupon(CouponRequest couponRequest);

	ResponseMessage updateCoupon(Long id, CouponRequest couponRequest);

	ResponseMessage expireCoupon(Long id);

	CouponResponse getCouponById(Long couponId);

	ResponseMessage issueMemberCoupon(MemberCouponIssueRequest memberCouponIssueRequest);

}
