package shop.nuribooks.view.admin.coupon.categorycoupon.service;

import shop.nuribooks.view.admin.coupon.categorycoupon.dto.CategoryCouponRequest;
import shop.nuribooks.view.admin.coupon.categorycoupon.dto.CategoryCouponResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

public interface CategoryCouponService {

	ResponseMessage registerCategoryCoupon(CategoryCouponRequest couponRequest);

	CategoryCouponResponse getCategoryCoupon(Long id);

}
