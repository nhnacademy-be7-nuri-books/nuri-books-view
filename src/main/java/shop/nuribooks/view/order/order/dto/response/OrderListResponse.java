package shop.nuribooks.view.order.order.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import shop.nuribooks.view.order.order.enums.OrderState;

public record OrderListResponse(
	Long orderId,
	LocalDateTime orderedAt,
	String title,
	BigDecimal paymentPrice,
	String orderInvoiceNumber,
	OrderState orderState
) {
}
