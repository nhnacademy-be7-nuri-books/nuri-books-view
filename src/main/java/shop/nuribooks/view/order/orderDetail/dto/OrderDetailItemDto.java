package shop.nuribooks.view.order.orderDetail.dto;

import java.math.BigDecimal;

import shop.nuribooks.view.order.order.enums.OrderState;

public record OrderDetailItemDto(
	String title,
	BigDecimal price,
	String thumbnailImageUrl,
	BigDecimal discountedPrice,
	int bookCount,
	OrderState orderState,
	boolean isWrapped
) {
}
