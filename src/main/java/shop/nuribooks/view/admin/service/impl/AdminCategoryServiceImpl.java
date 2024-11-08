package shop.nuribooks.view.admin.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.category.dto.CategoryRequest;
import shop.nuribooks.view.admin.category.dto.CategoryRespose;
import shop.nuribooks.view.admin.category.dto.SimpleCategoryResponse;
import shop.nuribooks.view.admin.category.feign.AdminCategoryClient;
import shop.nuribooks.view.admin.service.AdminCategoryService;

@RequiredArgsConstructor
@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {

	private final AdminCategoryClient adminCategoryClient;

	@Override
	public List<SimpleCategoryResponse> getAllCategories() {
		return adminCategoryClient.getAllCategories();
	}

	@Override
	public CategoryRespose getCategory(Long categoryId) {
		return adminCategoryClient.getCategory(categoryId);
	}

	@Override
	public void registerMainCategory(CategoryRequest categoryRequest) {
		adminCategoryClient.registerMainCategory(categoryRequest);
	}

	@Override
	public void updateCategory(Long categoryId, CategoryRequest categoryRequest) {
		adminCategoryClient.updateCategory(categoryRequest, categoryId);
	}

}