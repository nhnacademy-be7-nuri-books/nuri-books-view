package shop.nuribooks.view.book.dto;

import java.util.List;
import java.util.Map;

public record BookContributorsResponse(
	AdminBookListResponse bookDetails,
	Map<String, List<String>> contributorsByRole
) {
}
