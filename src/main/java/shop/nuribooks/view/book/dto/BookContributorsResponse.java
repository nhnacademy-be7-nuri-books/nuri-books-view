package shop.nuribooks.view.book.dto;

import java.util.List;
import java.util.Map;

import shop.nuribooks.view.bookcontributor.dto.BookContributorInfoResponse;

public record BookContributorsResponse(
	AdminBookListResponse bookDetails,
	Map<String, List<String>> contributorsByRole
) {
}
