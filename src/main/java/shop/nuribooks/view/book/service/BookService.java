package shop.nuribooks.view.book.service;

import shop.nuribooks.view.book.dto.AdminBookListResponse;
import shop.nuribooks.view.common.dto.PagedResponse;

public interface BookService {
	PagedResponse<AdminBookListResponse> getBooks(int page, int size);
}
