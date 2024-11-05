package shop.nuribooks.view.book.dto;

import java.math.BigDecimal;

public record AdminBookListResponse(
	Long id,
	String publisherName,
	String state,
	String title,
	BigDecimal price,
	BigDecimal salePrice,
	int discountRate,
	boolean isPackageable,
	int stock
) {
}
