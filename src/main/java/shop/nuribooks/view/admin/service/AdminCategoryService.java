package shop.nuribooks.view.admin.service;

import java.util.List;

import shop.nuribooks.view.admin.category.dto.CategoryRequest;
import shop.nuribooks.view.admin.category.dto.CategoryRespose;
import shop.nuribooks.view.admin.category.dto.SimpleCategoryResponse;

public interface AdminCategoryService {

	List<SimpleCategoryResponse> getAllCategories();

	CategoryRespose getCategory(Long categoryId);

	void registerMainCategory(CategoryRequest categoryRequest);

	void updateCategory(Long categoryId, CategoryRequest categoryRequest);
}
