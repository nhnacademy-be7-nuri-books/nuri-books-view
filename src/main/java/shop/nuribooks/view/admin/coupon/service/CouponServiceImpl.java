package shop.nuribooks.view.admin.coupon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.category.feign.AdminCategoryClient;
import shop.nuribooks.view.admin.coupon.dto.BookCouponRequest;
import shop.nuribooks.view.admin.coupon.dto.BookCouponResponse;
import shop.nuribooks.view.admin.coupon.dto.CategoryCouponRequest;
import shop.nuribooks.view.admin.coupon.dto.CategoryCouponResponse;
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
	private final AdminCategoryClient adminCategoryClient;

	@Override
	public Page<CouponResponse> getCoupons(CouponType type, Pageable pageable) {
		return couponServiceClient.getCoupons(type, pageable).getBody();
	}

	@Override
	public BookCouponResponse getBookCoupon(Long id) {
		return this.couponServiceClient.getBookCoupon(id).getBody();
	}

	@Override
	public CategoryCouponResponse getCategoryCoupon(Long id) {
		return this.couponServiceClient.getCategoryCoupon(id).getBody();
	}

	@Override
	public ResponseMessage registerCoupon(CouponRequest couponRequest) {
		return couponServiceClient.registerCoupon(couponRequest).getBody();
	}

	@Override
	public ResponseMessage registerBookCoupon(BookCouponRequest bookCouponRequest) {
		return couponServiceClient.registerBookCoupon(bookCouponRequest).getBody();
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
	public ResponseMessage registerCategoryCoupon(CategoryCouponRequest couponRequest) {
		return couponServiceClient.registerCategoryCoupon(couponRequest).getBody();
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
