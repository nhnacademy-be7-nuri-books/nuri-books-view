package shop.nuribooks.view.admin.publisher.dto;

import lombok.Builder;

@Builder
public record PublisherResponse(Long id, String name) {
	public static PublisherResponse error(String message) {
		return new PublisherResponse(null, message);
	}
}
