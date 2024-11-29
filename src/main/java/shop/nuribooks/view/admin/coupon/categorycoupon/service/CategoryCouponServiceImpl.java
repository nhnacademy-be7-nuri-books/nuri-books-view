package shop.nuribooks.view.admin.coupon.categorycoupon.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.coupon.categorycoupon.dto.CategoryCouponRequest;
import shop.nuribooks.view.admin.coupon.categorycoupon.dto.CategoryCouponResponse;
import shop.nuribooks.view.admin.coupon.categorycoupon.feign.CategoryCouponServiceClient;
import shop.nuribooks.view.common.dto.ResponseMessage;

@Service
@RequiredArgsConstructor
public class CategoryCouponServiceImpl implements CategoryCouponService {
	private final CategoryCouponServiceClient categoryCouponServiceClient;

	@Override
	public ResponseMessage registerCategoryCoupon(CategoryCouponRequest couponRequest) {
		return categoryCouponServiceClient.registerCategoryCoupon(couponRequest).getBody();
	}

	@Override
	public CategoryCouponResponse getCategoryCoupon(Long id) {
		return this.categoryCouponServiceClient.getCategoryCoupon(id).getBody();
	}
}
