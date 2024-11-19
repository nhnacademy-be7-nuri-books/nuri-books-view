package shop.nuribooks.view.admin.coupon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shop.nuribooks.view.admin.coupon.dto.CouponRequest;
import shop.nuribooks.view.admin.coupon.dto.CouponResponse;
import shop.nuribooks.view.admin.point.dto.PointPolicyResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

public interface CouponService {
	Page<CouponResponse> getCoupons(Pageable pageable);

	ResponseMessage registerCoupon(CouponRequest couponRequest);

	ResponseMessage updateCoupon(Long id, CouponRequest couponRequest);

	ResponseMessage deleteCoupon(Long id);
}
