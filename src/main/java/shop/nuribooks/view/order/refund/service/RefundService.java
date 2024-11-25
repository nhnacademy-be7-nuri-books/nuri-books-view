package shop.nuribooks.view.order.refund.service;

import shop.nuribooks.view.order.refund.dto.request.RefundRequest;
import shop.nuribooks.view.order.refund.dto.response.RefundInfoResponse;

public interface RefundService {
	RefundInfoResponse getRefundResponse(Long orderId);

	void registerRefund(Long orderId, RefundRequest refundRequest);
}
