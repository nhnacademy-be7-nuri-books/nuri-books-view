package shop.nuribooks.view.admin.category.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.category.dto.CategoryRequest;
import shop.nuribooks.view.admin.category.dto.CategoryResponse;
import shop.nuribooks.view.admin.category.dto.CategoryTreeResponse;
import shop.nuribooks.view.admin.category.feign.AdminCategoryClient;
import shop.nuribooks.view.admin.category.service.AdminCategoryService;
import shop.nuribooks.view.common.util.ExceptionUtil;
import shop.nuribooks.view.exception.BadRequestException;
import shop.nuribooks.view.exception.ResourceAlreadyExistsException;
import shop.nuribooks.view.exception.ResourceNotFoundException;

@RequiredArgsConstructor
@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {

	private final AdminCategoryClient adminCategoryClient;

	@Override
	public List<CategoryResponse> getAllCategories() {
		return adminCategoryClient.getAllCategories();
	}

	@Override
	public CategoryResponse getCategory(Long categoryId) {
		try {
			return adminCategoryClient.getCategory(categoryId);
		} catch (FeignException ex) {
			if (ex.status() == HttpStatus.NOT_FOUND.value()) {
				throw new ResourceNotFoundException(ex.getMessage());
			}
			ExceptionUtil.handleFeignException(ex);
		}
		return null;
	}

	@Override
	public void registerMainCategory(CategoryRequest categoryRequest) {
		try {
			adminCategoryClient.registerMainCategory(categoryRequest);
		} catch (FeignException ex) {
			if (ex.status() == HttpStatus.BAD_REQUEST.value()) {
				throw new BadRequestException(ex.getMessage());
			}
			ExceptionUtil.handleFeignException(ex);
		}
	}

	@Override
	public void registerSubCategory(Long categoryId, CategoryRequest categoryRequest) {
		try {
			adminCategoryClient.registerSubCategory(categoryRequest, categoryId);
		} catch (FeignException ex) {
			if (ex.status() == HttpStatus.BAD_REQUEST.value()) {
				throw new BadRequestException(ex.getMessage());
			} else if (ex.status() == HttpStatus.NOT_FOUND.value()) {
				throw new ResourceNotFoundException(ex.getMessage());
			}
			ExceptionUtil.handleFeignException(ex);
		}

	}

	@Override
	public void updateCategory(Long categoryId, CategoryRequest categoryRequest) {
		try {
			adminCategoryClient.updateCategory(categoryRequest, categoryId);
		} catch (FeignException ex) {
			if (ex.status() == HttpStatus.NOT_FOUND.value()) {
				throw new ResourceNotFoundException(ex.getMessage());
			} else if (ex.status() == HttpStatus.BAD_REQUEST.value()) {
				throw new BadRequestException(ex.getMessage());
			}
			ExceptionUtil.handleFeignException(ex);
		}
	}

	@Override
	public void deleteCategory(Long categoryId) {
		try {
			adminCategoryClient.deleteCategory(categoryId);
		} catch (FeignException ex) {
			if (ex.status() == HttpStatus.NOT_FOUND.value()) {
				throw new ResourceNotFoundException(ex.getMessage());
			}
			ExceptionUtil.handleFeignException(ex);
		}
	}

	@Override
	public List<CategoryTreeResponse> getAllCategoryTree() {
		return adminCategoryClient.getAllCategoryTree();
	}

}
