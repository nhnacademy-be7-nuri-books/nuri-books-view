package shop.nuribooks.view.member.coupon.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record MemberCouponResponse(
	@NotNull Long id,
	@NotNull Long couponId,
	@NotNull Long memberId,
	@NotNull boolean isUsed,
	@NotNull LocalDate createdAt,
	@NotNull LocalDate expiredAt) {
}
