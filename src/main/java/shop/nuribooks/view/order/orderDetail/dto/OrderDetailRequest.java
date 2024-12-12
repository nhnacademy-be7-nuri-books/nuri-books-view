package shop.nuribooks.view.order.orderDetail.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record OrderDetailRequest(
	@NotNull(message = "책 ID는 필수 입니다.")
	Long bookId, // 책 정보
	@Positive(message = "주문 수량은 양수입니다.")
	int count, // 주문 수량
	@PositiveOrZero(message = "단가는 0 이상이어야 합니다.")
	BigDecimal unitPrice, // 단가

	//nullable
	boolean isWrapped // 포장 여부
) {

}
