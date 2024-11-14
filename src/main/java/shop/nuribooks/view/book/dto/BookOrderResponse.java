package shop.nuribooks.view.book.dto;

import java.math.BigDecimal;
import java.util.List;

import shop.nuribooks.view.bookcontributor.dto.BookContributorInfoResponse;

public record BookOrderResponse(
	Long bookId,
	String title,
	String thumbnailImageUrl,
	BigDecimal price,
	int discountRate,
	BigDecimal salePrice,
	boolean isPackageable,
	int stock,
	List<BookContributorInfoResponse> contributors,
	int quantity,
	BigDecimal bookTotalPrice
) {

}
