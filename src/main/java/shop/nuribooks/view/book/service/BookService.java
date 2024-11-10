package shop.nuribooks.view.book.service;

import shop.nuribooks.view.book.dto.BookContributorsResponse;
import shop.nuribooks.view.book.dto.BookResponse;
import shop.nuribooks.view.common.dto.PagedResponse;

public interface BookService {
	PagedResponse<BookContributorsResponse> getBooks(int page, int size);
	BookResponse getBookById(Long bookId);
}
