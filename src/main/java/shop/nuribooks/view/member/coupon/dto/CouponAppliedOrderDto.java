package shop.nuribooks.view.member.coupon.dto;

import java.math.BigDecimal;

import shop.nuribooks.view.admin.coupon.enums.CouponType;

public record CouponAppliedOrderDto(
	String name,
	BigDecimal discountPrice,
	CouponType couponType
) {
}
