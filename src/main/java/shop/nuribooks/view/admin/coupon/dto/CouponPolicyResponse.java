package shop.nuribooks.view.admin.coupon.dto;

import java.math.BigDecimal;

import shop.nuribooks.view.admin.coupon.enums.DiscountType;

public record CouponPolicyResponse(
	Long id,

	String name,

	DiscountType discountType,

	BigDecimal minimumOrderPrice,

	BigDecimal maximumDiscountPrice,

	int discount
) {
}
