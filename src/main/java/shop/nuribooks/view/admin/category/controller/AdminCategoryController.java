package shop.nuribooks.view.admin.category.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.category.dto.CategoryRequest;
import shop.nuribooks.view.admin.category.dto.CategoryResponse;
import shop.nuribooks.view.admin.category.service.AdminCategoryService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/category")
public class AdminCategoryController {

	private final AdminCategoryService adminCategoryService;

	@GetMapping
	public String getAllCategories(Model model) {
		List<CategoryResponse> categoryList = adminCategoryService.getAllCategories();
		model.addAttribute("categories", categoryList);
		return "admin/category/category-manage";
	}

	@GetMapping("/update-delete/{categoryId}")
	public String manageCategory(@PathVariable Long categoryId, Model model) {
		// 특정 카테고리를 가져와서 관리 페이지로 넘겨줍니다.
		CategoryResponse categoryResponse = adminCategoryService.getCategory(categoryId);
		model.addAttribute("category", categoryResponse);
		return "admin/category/category-update-delete";
	}

	@GetMapping("/register")
	public String getRegisterCategoryPage(@RequestParam(required = false) Long parentId, Model model) {
		model.addAttribute("parentId", parentId);
		return "admin/category/category-register";
	}

	@PostMapping("/update/{categoryId}")
	public String updateCategory(@PathVariable Long categoryId,
		@ModelAttribute CategoryRequest categoryRequest) {
		adminCategoryService.updateCategory(categoryId, categoryRequest);
		return "redirect:/admin/category";
	}

	@PostMapping("/register")
	public String registerCategory(@ModelAttribute CategoryRequest categoryRequest,
		@RequestParam(required = false) Long parentId) {
		// 대분류와 소분류 구분하여 카테고리 추가 요청 처리
		if (parentId == null) {
			adminCategoryService.registerMainCategory(categoryRequest);
		} else {
			adminCategoryService.registerSubCategory(parentId, categoryRequest);
		}
		return "redirect:/admin/category";
	}

	@PostMapping("/delete/{categoryId}")
	public String deleteCategory(@PathVariable Long categoryId) {
		adminCategoryService.deleteCategory(categoryId);
		return "redirect:/admin/category";
	}
}
