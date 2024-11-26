package shop.nuribooks.view.order.refund.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.order.refund.dto.request.RefundRequest;
import shop.nuribooks.view.order.refund.dto.response.RefundInfoResponse;
import shop.nuribooks.view.order.refund.feign.RefundServiceClient;

@RequiredArgsConstructor
@Service
public class RefundServiceImpl implements RefundService {

	private final RefundServiceClient refundServiceClient;

	@Override
	public RefundInfoResponse getRefundResponse(Long orderId) {
		return refundServiceClient.getRefundInfoResponse(orderId).getBody();
	}

	@Override
	public void registerRefund(Long orderId, RefundRequest refundRequest) {
		refundServiceClient.registerRefund(orderId, refundRequest);
	}
}
