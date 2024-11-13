package shop.nuribooks.view.admin.category.dto;

import java.util.List;

public record CategoryResponse(Long id,
                               String name,
                               List<CategoryResponse> subCategories, Long parentId) {
}
