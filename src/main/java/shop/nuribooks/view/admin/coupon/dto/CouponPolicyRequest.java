package shop.nuribooks.view.admin.coupon.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import shop.nuribooks.view.admin.coupon.enums.DiscountType;

public record CouponPolicyRequest(
	@NotNull(message = "이름은 필수입니다.")
	@Size(min = 2, max = 50)
	String name,

	@NotNull(message = "쿠폰 할인 유형은 필수입니다.")
	DiscountType discountType,

	@NotNull(message = "최소 주문 금액은 필수입니다.")
	@Digits(integer = 10, fraction = 0)
	@PositiveOrZero(message = "최소 주문 금액은 0 이상이어야 합니다.")
	BigDecimal minimumOrderPrice,

	@Digits(integer = 10, fraction = 0)
	BigDecimal maximumDiscountPrice,

	@NotNull(message = "할인값은 필수입니다.")
	@Min(value = 1, message = "할인값은 필수입니다.")
	int discount
) {
}
