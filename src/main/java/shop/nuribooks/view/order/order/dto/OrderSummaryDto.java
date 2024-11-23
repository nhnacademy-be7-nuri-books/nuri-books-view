package shop.nuribooks.view.order.order.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record OrderSummaryDto(
	String title,
	LocalDateTime orderedAt
) {
}
