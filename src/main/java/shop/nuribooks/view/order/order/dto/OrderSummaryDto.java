package shop.nuribooks.view.order.order.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import shop.nuribooks.view.admin.wrappingpaper.dto.WrappingPaperResponse;

@Builder
public record OrderSummaryDto(
	String title,
	LocalDateTime orderedAt,
	WrappingPaperResponse wrappingInfo
) {
}
