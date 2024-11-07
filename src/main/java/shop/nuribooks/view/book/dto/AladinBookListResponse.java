package shop.nuribooks.view.book.dto;

import java.util.List;

public record AladinBookListResponse(
	List<AladinBookListItemResponse> item
) {
}
