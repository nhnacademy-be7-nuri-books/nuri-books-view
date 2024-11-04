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
	String categoryName
) {
}
