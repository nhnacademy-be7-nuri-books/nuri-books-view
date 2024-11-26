package shop.nuribooks.view.order.refund.dto.request;

import java.math.BigDecimal;

public record RefundRequest(BigDecimal refundAmount,
							String reason) {
}
