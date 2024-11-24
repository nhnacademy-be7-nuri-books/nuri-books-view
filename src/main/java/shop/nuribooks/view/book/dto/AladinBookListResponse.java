package shop.nuribooks.view.book.dto;

import java.util.List;

public record BookListResponse(
	List<BookListItemResponse> item
) {
}
