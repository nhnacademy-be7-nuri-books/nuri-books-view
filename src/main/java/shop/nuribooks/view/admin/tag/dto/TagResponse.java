package shop.nuribooks.view.admin.tag.dto;

import lombok.Builder;

@Builder
public record TagResponse(Long id, String name) {
}
