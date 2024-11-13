package shop.nuribooks.view.admin.category.service.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.admin.category.dto.CategoryRequest;
import shop.nuribooks.view.admin.category.dto.CategoryResponse;
import shop.nuribooks.view.admin.category.dto.CategoryTreeResponse;
import shop.nuribooks.view.admin.category.feign.AdminCategoryClient;
import shop.nuribooks.view.admin.category.service.AdminCategoryService;
import shop.nuribooks.view.common.dto.ResponseMessage;
import shop.nuribooks.view.common.util.ExceptionUtil;
import shop.nuribooks.view.exception.BadRequestException;
import shop.nuribooks.view.exception.ResourceNotFoundException;

@Slf4j
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
	public ResponseEntity<ResponseMessage> updateCategory(Long categoryId, CategoryRequest categoryRequest) {
		ResponseEntity<ResponseMessage> message;
		try {
			message = adminCategoryClient.updateCategory(categoryRequest, categoryId);
		} catch (FeignException.BadRequest e) {
			log.error("Bad request when updating category: {}", e.getMessage());
			throw new BadRequestException("잘못된 요청입니다.");
		} catch (FeignException.NotFound e) {
			log.error("Category not found: {}", e.getMessage());
			throw new ResourceNotFoundException("해당 카테고리를 찾을 수 없습니다.");
		} catch (FeignException e) {
			log.error("Feign client exception: {}", e.getMessage());
			throw new RuntimeException("Feign 클라이언트 호출 중 오류가 발생했습니다.");
		}
		return message;
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

	@Override
	public CategoryRequest getCategoryName(Long categoryId) {
		return adminCategoryClient.getCategoryName(categoryId);
	}
}
