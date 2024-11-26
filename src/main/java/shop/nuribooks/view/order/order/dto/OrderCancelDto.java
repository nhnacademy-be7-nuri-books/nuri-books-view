package shop.nuribooks.view.order.order.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Builder;
import shop.nuribooks.view.member.coupon.dto.CouponAppliedOrderDto;

@Builder
public record OrderCancelDto(
	BigDecimal paymentPrice,
	List<CouponAppliedOrderDto> bookAppliedCouponList, // 도서 전용 쿠폰
	BigDecimal usingPoint
) {
}
