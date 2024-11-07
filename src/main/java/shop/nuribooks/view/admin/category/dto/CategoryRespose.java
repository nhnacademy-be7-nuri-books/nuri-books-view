package shop.nuribooks.view.admin.category.dto;

import java.util.List;

public record CategoryRespose(Long id,
                              String name,
                              List<SimpleCategoryResponse> subCategories) {
}
