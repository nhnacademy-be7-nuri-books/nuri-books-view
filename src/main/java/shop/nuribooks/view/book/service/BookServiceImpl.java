package shop.nuribooks.view.book.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.category.dto.CategoryResponse;
import shop.nuribooks.view.book.dto.AladinBookRegisterRequest;
import shop.nuribooks.view.book.dto.BookContributorsResponse;
import shop.nuribooks.view.book.dto.BookResponse;
import shop.nuribooks.view.book.dto.BookUpdateRequest;
import shop.nuribooks.view.book.dto.PersonallyBookRegisterRequest;
import shop.nuribooks.view.book.dto.TopBookResponse;
import shop.nuribooks.view.book.feign.BookServiceClient;
import shop.nuribooks.view.common.dto.PagedResponse;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
	private final BookServiceClient bookServiceClient;

	@Override
	public Page<BookContributorsResponse> getBooks(Pageable pageable) {
		return bookServiceClient.getBooks(pageable);
	}

	@Override
	public BookResponse getBookById(Long bookId) {
		return bookServiceClient.getBookById(bookId, false);
	}

	@Override
	public BookResponse getBookByIdAndUpdateRecentView(Long bookId, Set<Long> recentViewSet) {
		Boolean updateRecentCount = true;
		if (recentViewSet.contains(bookId)) {
			updateRecentCount = false;
		}
		return bookServiceClient.getBookById(bookId, updateRecentCount);
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

	@Override
	public String uploadImage(MultipartFile file) {
		return bookServiceClient.uploadImage(file);
	}

	@Override
	public List<BookResponse> getAllBooks() {
		return bookServiceClient.getAllBooks();
	}

	@Override
	public List<TopBookResponse> getTopBookLikes() {
		return bookServiceClient.getTopBookLike();
	}

	@Override
	public List<TopBookResponse> getTopBookScores() {
		return bookServiceClient.getTopBookScore();
	}
}
