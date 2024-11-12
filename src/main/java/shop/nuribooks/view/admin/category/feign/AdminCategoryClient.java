package shop.nuribooks.view.admin.category.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import shop.nuribooks.view.admin.category.dto.CategoryRequest;
import shop.nuribooks.view.admin.category.dto.CategoryResponse;
import shop.nuribooks.view.admin.category.dto.CategoryTreeResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

/**
 * AdminCategory 관련 FeignClient
 *
 * @author janghyun
 */
@FeignClient(name = "categoryClient", url = "http://localhost:8080")
public interface AdminCategoryClient {

	@GetMapping("/api/categories")
	List<CategoryResponse> getAllCategories();

	@GetMapping("/api/categories/{categoryId}")
	CategoryResponse getCategory(@PathVariable Long categoryId);

	@PostMapping("/api/categories")
	ResponseEntity<ResponseMessage> registerMainCategory(@RequestBody CategoryRequest categoryRequest);

	@PostMapping("/api/categories/{categoryId}")
	ResponseEntity<ResponseMessage> registerSubCategory(@RequestBody CategoryRequest categoryRequest,
		@PathVariable Long categoryId);

	@PutMapping("/api/categories/{categoryId}")
	ResponseEntity<ResponseMessage> updateCategory(@RequestBody CategoryRequest categoryRequest,
		@PathVariable Long categoryId);

	@DeleteMapping("/api/categories/{categoryId}")
	ResponseEntity<Void> deleteCategory(@PathVariable Long categoryId);

	@GetMapping("/api/categories/tree")
	List<CategoryTreeResponse> getAllCategoryTree();
}
