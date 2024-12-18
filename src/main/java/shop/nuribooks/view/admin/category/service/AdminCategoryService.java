package shop.nuribooks.view.admin.category.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import shop.nuribooks.view.admin.category.dto.CategoryRequest;
import shop.nuribooks.view.admin.category.dto.CategoryResponse;
import shop.nuribooks.view.admin.category.dto.CategoryTreeResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

public interface AdminCategoryService {

	List<CategoryResponse> getAllCategories();

	CategoryResponse getCategory(Long categoryId);

	void registerMainCategory(CategoryRequest categoryRequest);

	void registerSubCategory(Long categoryId, CategoryRequest categoryRequest);

	ResponseEntity<ResponseMessage> updateCategory(Long categoryId, CategoryRequest categoryRequest);

	void deleteCategory(Long categoryId);

	List<CategoryTreeResponse> getAllCategoryTree();

	CategoryRequest getCategoryName(Long categoryId);
}
