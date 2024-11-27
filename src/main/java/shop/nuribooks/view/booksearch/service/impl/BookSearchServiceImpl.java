package shop.nuribooks.view.booksearch.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.booksearch.dto.BookSearchResponse;
import shop.nuribooks.view.booksearch.enums.SearchType;
import shop.nuribooks.view.booksearch.enums.SortType;
import shop.nuribooks.view.booksearch.feign.BookSearchServiceClient;
import shop.nuribooks.view.booksearch.service.BookSearchService;

@Service
@RequiredArgsConstructor
public class BookSearchServiceImpl implements BookSearchService {
	private final BookSearchServiceClient bookSearchServiceClient;

	@Override
	public Page<BookSearchResponse> getSearchResult(String keyword, Long categoryId, SearchType searchType,
		SortType sortType,
		Pageable pageable) {
		return bookSearchServiceClient.getSearchResult(keyword, categoryId, searchType, sortType, pageable);
	}
}
