package shop.nuribooks.view.admin.category.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import shop.nuribooks.view.admin.category.dto.CategoryRequest;
import shop.nuribooks.view.admin.category.dto.CategoryRespose;
import shop.nuribooks.view.admin.category.dto.SimpleCategoryResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

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

	@PostMapping("/api/categories")
	ResponseEntity<ResponseMessage> registerMainCategory(@RequestBody CategoryRequest categoryRequest);

	@PatchMapping("/api/categories/{categoryId}")
	ResponseEntity<ResponseMessage> updateCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable Long categoryId);


}
