package shop.nuribooks.view.admin.tag.dto;

import lombok.Builder;

@Builder
public record TagResponse(Long id, String name) {
	public static TagResponse error(String message) {
		return new TagResponse(null, message);
	}
}
