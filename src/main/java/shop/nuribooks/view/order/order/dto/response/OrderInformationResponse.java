package shop.nuribooks.view.order.order.dto.response;

import java.util.List;

import shop.nuribooks.view.admin.shippingpolicy.dto.ShippingPolicyResponse;
import shop.nuribooks.view.admin.wrappingpaper.dto.WrappingPaperResponse;
import shop.nuribooks.view.book.dto.BookOrderResponse;
import shop.nuribooks.view.member.coupon.dto.MemberCouponOrderDto;
import shop.nuribooks.view.member.customer.CustomerDto;

public record OrderInformationResponse(

	// 사용자 관련 정보
	CustomerDto customer,

	// 상품 리스트
	List<BookOrderResponse> bookOrderResponse,

	// 배송비 관련 정보
	ShippingPolicyResponse shippingPolicyResponse,

	// 포장지 정보
	List<WrappingPaperResponse> paperResponse,

	// 쿠폰 정보
	List<MemberCouponOrderDto> allTypeCoupon,

	// 특이 사항 시 발생되는 메시지
	String message

) {

	public static OrderInformationResponse error(String message) {
		return new OrderInformationResponse(
			null,
			null,
			null,
			null,
			null,
			message);
	}
}