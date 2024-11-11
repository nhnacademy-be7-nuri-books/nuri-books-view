package shop.nuribooks.view.admin.category.dto;

import lombok.Builder;

@Builder
public record SimpleCategoryResponse(Long id, String name) {
}
