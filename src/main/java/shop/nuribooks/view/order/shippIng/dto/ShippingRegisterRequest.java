package shop.nuribooks.view.order.shippIng.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ShippingRegisterRequest(
	@NotNull(message = "배송 정책은 필수입니다.")
	Long shippingPolicyId,

	@NotBlank(message = "받는 사람 이름은 필수 입니다.")
	String recipientName,

	@NotBlank(message = "받는 사람 주소는 필수 입니다.")
	String recipientAddress,

	@NotBlank(message = "받는 사람 상세 주소는 필수 입니다.")
	String recipientAddressDetail,

	@NotBlank(message = "받는 사람 우편 번호는 필수 입니다.")
	String recipientZipcode,

	@NotBlank(message = "받는 사람 연락처는 필수 입니다.")
	String recipientPhoneNumber,

	@NotBlank(message = "보내는 사람 이름은 필수 입니다.")
	String senderName,

	@NotBlank(message = "보내는 사람 연락처 필수 입니다.")
	String senderPhoneNumber
) {
}
