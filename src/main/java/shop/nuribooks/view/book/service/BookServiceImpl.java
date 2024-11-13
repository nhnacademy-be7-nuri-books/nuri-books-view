package shop.nuribooks.view.book.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.category.dto.CategoryResponse;
import shop.nuribooks.view.book.dto.AladinBookRegisterRequest;
import shop.nuribooks.view.book.dto.BookContributorsResponse;
import shop.nuribooks.view.book.dto.BookResponse;
import shop.nuribooks.view.book.dto.BookUpdateRequest;
import shop.nuribooks.view.book.dto.PersonallyBookRegisterRequest;
import shop.nuribooks.view.book.feign.BookServiceClient;
import shop.nuribooks.view.common.dto.PagedResponse;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
	private final BookServiceClient bookServiceClient;

	@Override
	public PagedResponse<BookContributorsResponse> getBooks(int page, int size) {
		return bookServiceClient.getBooks(page, size);
	}

	@Override
	public BookResponse getBookById(Long bookId) {
		return bookServiceClient.getBookById(bookId);
	}

	// 알라딘 도서 등록
	@Override
	public void registerAladinBook(AladinBookRegisterRequest aladinRequest) {
		bookServiceClient.registerAladinBook(aladinRequest);
	}

	// 직접 도서 등록
	@Override
	public void registerPersonallyBook(PersonallyBookRegisterRequest personallyRequest) {
		bookServiceClient.registerPersonallyBook(personallyRequest);
	}

	@Override
	public PagedResponse<BookContributorsResponse> getBooksByCategoryId(Long categoryId, int page, int size) {
		return bookServiceClient.getBooksByCategoryId(categoryId, page, size);
	}

	@Override
	public void updateBook(Long bookId, BookUpdateRequest bookUpdateRequest) {
		bookServiceClient.updateBook(bookId, bookUpdateRequest);
	}

	@Override
	public void deleteBook(Long bookId) {
		bookServiceClient.deleteBook(bookId);
	}

	@Override
	public List<CategoryResponse> getAllCategories() {
		return bookServiceClient.getAllCategories();
	}
}
