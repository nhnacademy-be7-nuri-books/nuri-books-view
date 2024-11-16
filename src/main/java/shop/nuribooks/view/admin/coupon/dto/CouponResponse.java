package shop.nuribooks.view.admin.coupon.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import shop.nuribooks.view.admin.coupon.enums.ExpirationType;

public record CouponResponse(
	Long id,
	String name,
	int discount,
	BigDecimal minimumOrderPrice,
	BigDecimal maximumDiscountPrice,
	LocalDate createdAt,
	LocalDate expiredAt,
	Integer period,
	ExpirationType expirationType
) {
}
