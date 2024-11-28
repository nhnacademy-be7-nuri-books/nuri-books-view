package shop.nuribooks.view.booksearch.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.nuribooks.view.booksearch.dto.BookSearchResponse;
import shop.nuribooks.view.booksearch.enums.SearchType;
import shop.nuribooks.view.booksearch.enums.SortType;

@FeignClient(name = "search", url = "http://localhost:8080")
public interface BookSearchServiceClient {
	@GetMapping("/api/books/search")
	Page<BookSearchResponse> getSearchResult(
		@RequestParam("keyword") String keyword,
		@RequestParam(name = "category_id", required = false) Long categoryId,
		@RequestParam(name = "search_type", required = false, defaultValue = "ALL") SearchType searchType,
		@RequestParam(name = "sort_type", required = false, defaultValue = "ACCURACY") SortType sortType,
		@PageableDefault Pageable pageable
	);

	@GetMapping("/api/books/search")
	Page<BookSearchResponse> getSearchResultWithoutCategoryId(
		@RequestParam("keyword") String keyword,
		@RequestParam(name = "search_type", required = false, defaultValue = "ALL") SearchType searchType,
		@RequestParam(name = "sort_type", required = false, defaultValue = "ACCURACY") SortType sortType,
		@PageableDefault Pageable pageable
	);
}
