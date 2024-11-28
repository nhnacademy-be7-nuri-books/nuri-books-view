package shop.nuribooks.view.booksearch.controller;

import java.util.Objects;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.admin.category.dto.CategoryRequest;
import shop.nuribooks.view.admin.category.service.AdminCategoryService;
import shop.nuribooks.view.booksearch.dto.BookSearchResponse;
import shop.nuribooks.view.booksearch.enums.SearchType;
import shop.nuribooks.view.booksearch.enums.SortType;
import shop.nuribooks.view.booksearch.service.BookSearchService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookSearchController {
	private final BookSearchService bookSearchService;
	private final AdminCategoryService adminCategoryService;

	@GetMapping("/search")
	public String getBookSearchResults(
		@RequestParam("keyword") String keyword,
		@RequestParam(value = "category_id", required = false) Long categoryId,
		@RequestParam(value = "search_type", required = false, defaultValue = "ALL") SearchType searchType,
		@RequestParam(value = "sort_type", required = false, defaultValue = "ACCURACY") SortType sortType,
		@PageableDefault Pageable pageable,
		Model model
	){
		Page<BookSearchResponse> bookSearchResponses = bookSearchService.getSearchResult(keyword, categoryId,
			searchType, sortType, pageable);
		model.addAttribute("pages", bookSearchResponses);
		model.addAttribute("sort_types", SortType.values());
		model.addAttribute("categoryId", categoryId);
		model.addAttribute("layout", "layouts/layout1");  // 일반 사용자 레이아웃

		CategoryRequest categoryName = null;
		if (Objects.nonNull(categoryId)) {
			categoryName = adminCategoryService.getCategoryName(categoryId);
		}
		model.addAttribute("categoryName", categoryName);

		return "book/bookSearchList";
	}
}
