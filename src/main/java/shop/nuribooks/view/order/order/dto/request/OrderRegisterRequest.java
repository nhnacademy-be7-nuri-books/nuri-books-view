package shop.nuribooks.view.order.order.dto.request;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import shop.nuribooks.view.member.customer.CustomerRegisterRequest;
import shop.nuribooks.view.order.orderDetail.dto.OrderDetailRequest;
import shop.nuribooks.view.order.shippIng.dto.ShippingRegisterRequest;

public record OrderRegisterRequest(
	@PositiveOrZero(message = "결제 금액은 0 이상이어야 합니다.")
	BigDecimal paymentPrice, // 결제 금액
	@PositiveOrZero(message = "결제 금액은 0 이상이어야 합니다.")
	BigDecimal paymentBooks, // 순수 도서 가격
	@JsonFormat(shape = STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	LocalDate expectedDeliveryAt, // 주문 예상 날짜
	@NotNull(message = "주문 상세는 1개 이상이어야 합니다.")
	List<OrderDetailRequest> orderDetails, // 주문 상세
	@NotNull(message = "배송 정보 입력은 필수 입니다.")
	ShippingRegisterRequest shippingRegister, // 배송 정보

	// nullable
	CustomerRegisterRequest customerRegister,
	@PositiveOrZero(message = "사용 포인트는 0 이상이어야 합니다.")
	BigDecimal usingPoint, // 사용 포인트
	Long allAppliedCoupon, // 주문 전용 쿠폰 (stub 객체 사용 이후 변경 예정)
	Long wrapping, // 포장지 정보
	String wrappingList // 포장지 적용 도서
) {

}
