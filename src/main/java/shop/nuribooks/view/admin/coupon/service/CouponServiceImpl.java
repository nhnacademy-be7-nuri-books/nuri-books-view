package shop.nuribooks.view.admin.coupon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.coupon.dto.CouponRequest;
import shop.nuribooks.view.admin.coupon.dto.CouponResponse;
import shop.nuribooks.view.admin.coupon.feign.CouponServiceClient;
import shop.nuribooks.view.common.dto.ResponseMessage;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService{
	private final CouponServiceClient couponServiceClient;

	@Override
	public Page<CouponResponse> getCoupons(Pageable pageable) {
		return this.couponServiceClient.getCoupons(pageable).getBody();
	}

	@Override
	public ResponseMessage registerCoupon(CouponRequest couponRequest) {
		return this.couponServiceClient.registerCoupon(couponRequest).getBody();
	}

	@Override
	public ResponseMessage updateCoupon(Long id, CouponRequest couponRequest) {
		return this.couponServiceClient.updateCoupon(id, couponRequest).getBody();
	}

	@Override
	public ResponseMessage expireCoupon(Long id) {
		return this.couponServiceClient.expireCoupon(id).getBody();
	}
}
