package shop.nuribooks.view.order.shippIng.dto;

import lombok.Builder;

@Builder
public record ShippingInfoDto(
	String recipientName,
	String recipientPhoneNumber,
	String recipientAddress,
	String recipientAddressDetail,
	String recipientZipcode
) {
}
