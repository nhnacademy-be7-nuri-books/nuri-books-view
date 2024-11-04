package shop.nuribooks.view.book.dto;

import java.math.BigDecimal;

public record AladinBookListItemResponse(
	String title,
	String author,
	String pubDate,
	String description,
	String isbn,
	String isbn13,
	BigDecimal priceSales,
	BigDecimal priceStandard,
	String stockStatus,
	String cover,
	String publisher,
	String categoryName
) {
}
