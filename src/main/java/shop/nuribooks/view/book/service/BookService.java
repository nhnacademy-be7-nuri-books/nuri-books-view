package shop.nuribooks.view.book.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import shop.nuribooks.view.admin.category.dto.CategoryResponse;
import shop.nuribooks.view.book.dto.AladinBookRegisterRequest;
import shop.nuribooks.view.book.dto.BookContributorsResponse;
import shop.nuribooks.view.book.dto.BookResponse;
import shop.nuribooks.view.book.dto.BookUpdateRequest;
import shop.nuribooks.view.book.dto.PersonallyBookRegisterRequest;
import shop.nuribooks.view.book.dto.TopBookLikeResponse;
import shop.nuribooks.view.common.dto.PagedResponse;

public interface BookService {
	Page<BookContributorsResponse> getBooks(Pageable pageable);

	BookResponse getBookById(Long bookId);
	
	BookResponse getBookByIdAndUpdateRecentView(Long bookId, Set<Long> recentViewSet);

	void registerAladinBook(AladinBookRegisterRequest aladinRequest);

	void registerPersonallyBook(PersonallyBookRegisterRequest personallyRequest);

	PagedResponse<BookContributorsResponse> getBooksByCategoryId(Long categoryId, int page, int size);

	void updateBook(Long bookId, BookUpdateRequest bookUpdateRequest);
  
	void deleteBook(Long bookId);

	List<CategoryResponse> getAllCategories();

	String uploadImage(MultipartFile file);

	List<TopBookLikeResponse> getTopBookLikes();
}
