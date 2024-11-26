package shop.nuribooks.view.order.refund.dto.response;

import java.math.BigDecimal;

public record RefundInfo(
	BigDecimal paymentAmount,
	BigDecimal shippingPrice,
	BigDecimal savingPointAmount,
	BigDecimal deductedAmount,
	BigDecimal totalRefundAmount
) {
}
