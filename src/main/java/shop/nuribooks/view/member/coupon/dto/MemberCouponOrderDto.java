package shop.nuribooks.view.member.coupon.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import shop.nuribooks.view.admin.point.enums.PolicyType;

public record MemberCouponOrderDto(
	@NotNull long couponId,
	@NotNull long memberCouponId,
	@NotNull String couponName,
	@NotNull PolicyType policyType,
	@NotNull int discount,
	@NotNull BigDecimal minimumOrderPrice,
	@NotNull BigDecimal maximumDiscountPrice,
	@NotNull boolean isUsed,
	@NotNull LocalDate createdAt,
	@NotNull LocalDate expiredAt) {
}
