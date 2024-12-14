package shop.nuribooks.view.member.coupon.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import shop.nuribooks.view.admin.coupon.enums.DiscountType;

public record MemberCouponOrderDto(
	@NotNull long couponId,
	@NotNull long memberCouponId,
	@NotNull String couponName,
	@NotNull DiscountType discountType,
	@NotNull int discount,
	@NotNull BigDecimal minimumOrderPrice,
	@NotNull BigDecimal maximumDiscountPrice,
	@NotNull boolean isUsed,
	@NotNull LocalDate createdAt,
	@NotNull LocalDate expiredAt,
	@NotNull BigDecimal discountPrice,
	List<Long> bookIds) {
}
