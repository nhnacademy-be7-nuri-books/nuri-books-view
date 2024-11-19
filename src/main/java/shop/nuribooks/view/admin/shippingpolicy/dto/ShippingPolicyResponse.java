package shop.nuribooks.view.admin.shippingpolicy.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ShippingPolicyResponse(
	Long id,
	Integer shippingFee,
	LocalDateTime expiration,
	BigDecimal minimumOrderPrice
) {
}
