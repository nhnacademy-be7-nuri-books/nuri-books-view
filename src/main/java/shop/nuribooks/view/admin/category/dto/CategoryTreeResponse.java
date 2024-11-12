package shop.nuribooks.view.admin.category.dto;

import java.util.List;

public record CategoryTreeResponse(
	Long id,
	String name,
	List<CategoryTreeResponse> subCategories
) {
}
