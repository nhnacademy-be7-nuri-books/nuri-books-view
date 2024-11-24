package shop.nuribooks.view.book.dto;

import java.math.BigDecimal;

public record AladinBookListItemResponse(
	String title,
	String author,
	String pubDate,
	String isbn,
	BigDecimal priceStandard,
	String stockStatus,
	String cover,
	String publisher,
	String categoryName,
	String description
) {
	public static AladinBookListItemResponse empty() {
		return new AladinBookListItemResponse(
			null, null, null, null, BigDecimal.ZERO, null, null, null, null, null
		);
	}
}
