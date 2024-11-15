package shop.nuribooks.view.order.order.dto;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import shop.nuribooks.view.member.customer.CustomerRegisterRequest;
import shop.nuribooks.view.order.orderDetail.dto.OrderDetailRequest;
import shop.nuribooks.view.order.shippong.dto.ShippingRegisterRequest;
import shop.nuribooks.view.order.stub.coupon.AllAppliedCouponRequestStub;
import shop.nuribooks.view.order.wrapping.dto.WrappingRegisterRequest;

public record OrderTempRegisterRequest(
	@PositiveOrZero(message = "결제 금액은 0 이상이어야 합니다.")
	BigDecimal paymentPrice, // 총 결제 금액 = 상품 금액 + 포장 금액 + 배송금액 - 포인트 - 쿠폰 할인가
	@PositiveOrZero(message = "포장 금액은 0 이상이어야 합니다.")
	BigDecimal wrappingPrice, // 포장 비용
	@JsonFormat(shape = STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	LocalDateTime orderedAt, // 주문 시간
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
	AllAppliedCouponRequestStub allAppliedCoupon, // 주문 전용 쿠폰 (stub 객체 사용 이후 변경 예정)
	WrappingRegisterRequest wrapping // 포장지 정보
) {

}
