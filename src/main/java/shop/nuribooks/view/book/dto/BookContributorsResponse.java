package shop.nuribooks.view.book.dto;

import java.util.List;

import shop.nuribooks.view.bookcontributor.dto.BookContributorInfoResponse;

public record BookContributorsResponse(
	AdminBookListResponse bookDetails,
	List<BookContributorInfoResponse> contributors
) {
}
