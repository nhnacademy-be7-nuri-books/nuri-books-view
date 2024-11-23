package shop.nuribooks.view.order.order.dto.response;

import java.math.BigDecimal;
import java.util.List;

import shop.nuribooks.view.book.dto.BookOrderResponse;
import shop.nuribooks.view.member.address.dto.response.AddressResponse;

public record OrderInformationResponse(

	// 사용자 관련 정보
	Long userId,
	String name,
	String phoneNumber,
	String email,
	BigDecimal point,
	List<AddressResponse> addressList,

	// 상품 리스트
	List<BookOrderResponse> bookOrderResponse,

	// 배송 관련 정보
	Long shippingPolicyId,
	int shippingFee,

	// 특이 사항 시 발생되는 메시지
	String message

	// todo: 쿠폰
) {

	public static OrderInformationResponse error(String message) {
		return new OrderInformationResponse(
			null,
			null,
			null,
			null,
			BigDecimal.ZERO,
			null,
			null,
			null,
			0,
			message);
	}
}