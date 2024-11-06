package shop.nuribooks.view.admin.category.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.category.dto.CategoryRespose;
import shop.nuribooks.view.admin.category.feign.AdminCategoryClient;
import shop.nuribooks.view.admin.category.dto.SimpleCategoryResponse;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminCategoryController {

	private final AdminCategoryClient adminCategoryClient;

	@GetMapping("/category")
	public String getAllCategories(Model model) {
		List<SimpleCategoryResponse> categoryList = adminCategoryClient.getAllCategories();
		model.addAttribute("categories", categoryList);
		return "admin/category";
	}

	@GetMapping("/category/{categoryId}")
	public String getCategory(@PathVariable Long categoryId, Model model) {
		CategoryRespose categoryRespose = adminCategoryClient.getCategory(categoryId);
		model.addAttribute("category", categoryRespose);
		return "admin/category";
	}
}