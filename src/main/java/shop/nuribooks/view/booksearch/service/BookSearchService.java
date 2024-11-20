package shop.nuribooks.view.booksearch.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import shop.nuribooks.view.booksearch.dto.BookSearchResponse;
import shop.nuribooks.view.booksearch.enums.SearchType;
import shop.nuribooks.view.booksearch.enums.SortType;

public interface BookSearchService {
	Page<BookSearchResponse> getSearchResult(String keyword, SearchType searchType, SortType sortType, Pageable pageable);
}
