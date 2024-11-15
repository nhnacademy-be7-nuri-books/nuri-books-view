package shop.nuribooks.view.book.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import shop.nuribooks.view.admin.category.dto.CategoryResponse;
import shop.nuribooks.view.book.dto.AladinBookRegisterRequest;
import shop.nuribooks.view.book.dto.BookContributorsResponse;
import shop.nuribooks.view.book.dto.BookResponse;
import shop.nuribooks.view.book.dto.BookUpdateRequest;
import shop.nuribooks.view.book.dto.PersonallyBookRegisterRequest;
import shop.nuribooks.view.common.dto.PagedResponse;

public interface BookService {
	PagedResponse<BookContributorsResponse> getBooks(int page, int size);

	BookResponse getBookById(Long bookId);

	void registerAladinBook(AladinBookRegisterRequest aladinRequest);

	void registerPersonallyBook(PersonallyBookRegisterRequest personallyRequest);

	PagedResponse<BookContributorsResponse> getBooksByCategoryId(Long categoryId, int page, int size);

	void updateBook(Long bookId, BookUpdateRequest bookUpdateRequest);
  
	void deleteBook(Long bookId);

	List<CategoryResponse> getAllCategories();

	String uploadImage(MultipartFile file);
}
