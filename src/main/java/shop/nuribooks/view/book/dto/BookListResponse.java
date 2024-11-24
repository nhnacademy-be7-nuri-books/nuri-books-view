package shop.nuribooks.view.book.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record BookListResponse(
	Long id,
	String publisherName,
	String state,
	String title,
	BigDecimal price,
	int discountRate,
	boolean isPackageable,
	String thumbnailImageUrl
) {
	public int getSalePrice() {
		return price.multiply(BigDecimal.valueOf(100L - discountRate))
			.divide(BigDecimal.valueOf(100), 0, RoundingMode.DOWN).intValue();
	}
}
