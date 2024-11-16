package shop.nuribooks.view.admin.coupon.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import shop.nuribooks.view.admin.coupon.enums.ExpirationType;
import shop.nuribooks.view.admin.point.enums.PolicyType;

public record CouponResponse(
	Long id,
	String name,
	PolicyType policyType,
	int discount,
	BigDecimal minimumOrderPrice,
	BigDecimal maximumDiscountPrice,
	LocalDate createdAt,
	LocalDate expiredAt,
	Integer period,
	ExpirationType expirationType
) {
}
