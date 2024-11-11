package shop.nuribooks.view.admin.category.service;

import java.util.List;

import shop.nuribooks.view.admin.category.dto.CategoryRequest;
import shop.nuribooks.view.admin.category.dto.CategoryResponse;
import shop.nuribooks.view.admin.category.dto.CategoryTreeResponse;

public interface AdminCategoryService {

	List<CategoryResponse> getAllCategories();

	CategoryResponse getCategory(Long categoryId);

	void registerMainCategory(CategoryRequest categoryRequest);

	void registerSubCategory(Long categoryId, CategoryRequest categoryRequest);

	void updateCategory(Long categoryId, CategoryRequest categoryRequest);

	void  deleteCategory(Long categoryId);

	List<CategoryTreeResponse> getAllCategoryTree();
}
