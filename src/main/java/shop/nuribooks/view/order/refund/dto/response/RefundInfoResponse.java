package shop.nuribooks.view.order.refund.dto.response;

import org.springframework.data.domain.Page;

import shop.nuribooks.view.order.orderDetail.dto.OrderDetailItemDto;
import shop.nuribooks.view.payment.dto.PaymentInfoDto;

public record RefundInfoResponse(
	Page<OrderDetailItemDto> orderItems, // 주문 항목 리스트
	PaymentInfoDto payment,
	RefundInfo refundInfo
) {
}
