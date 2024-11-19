package shop.nuribooks.view.admin.shippingpolicy.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ShippingPolicyRequest(
	@NotNull(message = "배송비 설정은 필수입니다.")
	Integer shippingFee,

	LocalDateTime expiration,

	@NotNull(message = "배송비 책정을 위한 최소주문금액은 필수입니다.")
	@Min(value = 0, message = "최소주문금액은 0원 이상이어야합니다.")
	BigDecimal minimumOrderPrice
) {
}
