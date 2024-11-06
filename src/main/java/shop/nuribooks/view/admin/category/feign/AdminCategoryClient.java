package shop.nuribooks.view.admin.category.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import shop.nuribooks.view.admin.category.dto.CategoryRespose;
import shop.nuribooks.view.admin.category.dto.SimpleCategoryResponse;

/**
 * AdminCategory 관련 FeignClient
 *
 * @author janghyun
 */
@FeignClient(name = "categoryClient", url = "http://localhost:8080")
public interface AdminCategoryClient {

	@GetMapping("/api/categories")
	List<SimpleCategoryResponse> getAllCategories();

	@GetMapping("/api/categories/{categoryId}")
	CategoryRespose getCategory(@PathVariable Long categoryId);

}
