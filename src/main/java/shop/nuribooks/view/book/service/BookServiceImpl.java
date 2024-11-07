package shop.nuribooks.view.book.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.book.dto.AdminBookListResponse;
import shop.nuribooks.view.book.feign.BookServiceClient;
import shop.nuribooks.view.common.dto.PagedResponse;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService{
	private final BookServiceClient bookServiceClient;
	@Override
	public PagedResponse<AdminBookListResponse> getBooks(int page, int size) {
		return bookServiceClient.getBooks(page, size);
	}
}
