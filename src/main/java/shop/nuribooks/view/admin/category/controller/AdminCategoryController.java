package shop.nuribooks.view.admin.category.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.category.dto.CategoryRequest;
import shop.nuribooks.view.admin.category.dto.CategoryRespose;
import shop.nuribooks.view.admin.category.dto.SimpleCategoryResponse;
import shop.nuribooks.view.admin.service.AdminCategoryService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/category")
public class AdminCategoryController {

	private final AdminCategoryService adminCategoryService;

	@GetMapping
	public String getAllCategories(Model model) {
		List<SimpleCategoryResponse> categoryList = adminCategoryService.getAllCategories();
		model.addAttribute("categories", categoryList);
		return "admin/category";
	}

	@GetMapping("/{categoryId}")
	public String getCategory(@PathVariable Long categoryId, Model model) {
		CategoryRespose categoryRespose = adminCategoryService.getCategory(categoryId);
		model.addAttribute("category", categoryRespose);
		return "admin/category";
	}

	@PostMapping
	public String registerMainCategory(@ModelAttribute CategoryRequest categoryRequest) {
		adminCategoryService.registerMainCategory(categoryRequest);
		return "redirect:/admin/category";
	}

	@PatchMapping("/{categoryId}")
	public String updateCategory(@PathVariable Long categoryId, @ModelAttribute CategoryRequest categoryRequest) {
		adminCategoryService.updateCategory(categoryId, categoryRequest);
		return "redirect:/admin/category";
	}
}