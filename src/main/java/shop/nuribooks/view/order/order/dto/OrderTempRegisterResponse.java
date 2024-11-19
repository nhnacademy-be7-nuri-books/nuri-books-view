package shop.nuribooks.view.order.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderTempRegisterResponse(
	Long orderId, // 주문 아이디
	String orderName, // 주문 명
	BigDecimal paymentPrice, // 결제 금액
	BigDecimal wrappingPrice, // 포장 비용
	LocalDateTime orderedAt // 주문 시간
) {

}
