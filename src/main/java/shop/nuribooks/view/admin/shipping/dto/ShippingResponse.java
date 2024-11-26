package shop.nuribooks.view.admin.shipping.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import shop.nuribooks.view.admin.shippingpolicy.dto.ShippingPolicyResponse;

@Builder
public record ShippingResponse(
	Long id,
	Long orderId,
	ShippingPolicyResponse shippingPolicy,
	String recipientName,
	String recipientAddress,
	String recipientAddressDetail,
	String recipientZipcode,
	String recipientPhoneNumber,
	String senderName,
	String senderPhoneNumber,
	LocalDateTime shippingAt,
	String orderInvoiceNumber,
	LocalDateTime shippingCompletedAt
) {
}
