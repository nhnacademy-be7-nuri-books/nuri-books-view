package shop.nuribooks.view.payment.dto;

import java.math.BigDecimal;

public record PaymentInfoDto(
	BigDecimal totalPrice,
	BigDecimal unitPrice,
	BigDecimal shippingPrice,
	BigDecimal orderCouponPrice,
	BigDecimal bookCouponPrice, // 도서 쿠폰 총 금액
	BigDecimal wrappingPrice,
	BigDecimal usingPoint,
	BigDecimal savingPoint
) {
}
