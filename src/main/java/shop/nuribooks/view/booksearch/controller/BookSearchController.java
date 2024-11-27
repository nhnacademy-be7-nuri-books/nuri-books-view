package shop.nuribooks.view.booksearch.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.booksearch.dto.BookSearchResponse;
import shop.nuribooks.view.booksearch.enums.SearchType;
import shop.nuribooks.view.booksearch.enums.SortType;
import shop.nuribooks.view.booksearch.service.BookSearchService;

@Controller
@RequiredArgsConstructor
public class BookSearchController {
	private final BookSearchService bookSearchService;

	@GetMapping("/search")
	public String getBookSearchResults(
		@RequestParam("keyword") String keyword,
		@RequestParam(value = "category_id", required = false) Long categoryId,
		@RequestParam(value = "search_type", required = false, defaultValue = "ALL") SearchType searchType,
		@RequestParam(value = "sort_type", required = false, defaultValue = "ACCURACY") SortType sortType,
		@PageableDefault Pageable pageable,
		Model model,
		HttpServletRequest request
	){
		Page<BookSearchResponse> bookSearchResponses = bookSearchService.getSearchResult(keyword, categoryId,
			searchType, sortType, pageable);
		model.addAttribute("pages", bookSearchResponses);
		model.addAttribute("sort_types", SortType.values());
		model.addAttribute("layout", "layouts/layout1");  // 일반 사용자 레이아웃
		String currentUrl = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString())
			.replaceQuery(request.getQueryString())
			.replaceQueryParam("page") // 'page' 파라미터 제거
			.replaceQueryParam("size") // 'page' 파라미터 제거
			.build()
			.toString();

		model.addAttribute("current_url", currentUrl);
		return "book/bookSearchList";
	}
}
