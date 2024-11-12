package shop.nuribooks.view.review.dto.response;

import lombok.Builder;

@Builder
public record ReviewImageResponse(
	long id,
	String imageUrl
) {
}
