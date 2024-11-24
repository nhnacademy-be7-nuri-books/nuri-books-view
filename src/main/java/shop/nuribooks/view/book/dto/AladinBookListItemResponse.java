package shop.nuribooks.view.book.dto;

import java.math.BigDecimal;

public record BookListItemResponse(
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
	public static BookListItemResponse empty() {
		return new BookListItemResponse(
			null, null, null, null, BigDecimal.ZERO, null, null, null, null, null
		);
	}
}
