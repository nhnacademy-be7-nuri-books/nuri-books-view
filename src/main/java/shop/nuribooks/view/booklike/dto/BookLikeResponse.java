package shop.nuribooks.view.booklike.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record BookLikeResponse(
	Long bookId,
	String title,
	Map<String, List<String>> contributorsByRole,
	String publisherName,
	BigDecimal price,
	int discountRate,
	BigDecimal salePrice,
	String thumbnailImageUrl
) {
}
