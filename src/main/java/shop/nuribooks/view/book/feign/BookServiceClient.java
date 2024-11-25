package shop.nuribooks.view.book.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import shop.nuribooks.view.admin.category.dto.CategoryResponse;
import shop.nuribooks.view.book.dto.AladinBookRegisterRequest;
import shop.nuribooks.view.book.dto.BookContributorsResponse;
import shop.nuribooks.view.book.dto.BookResponse;
import shop.nuribooks.view.book.dto.BookUpdateRequest;
import shop.nuribooks.view.book.dto.PersonallyBookRegisterRequest;
import shop.nuribooks.view.book.dto.TopBookLikeResponse;
import shop.nuribooks.view.common.dto.PagedResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

@FeignClient(name = "book", url = "http://localhost:8080")
public interface BookServiceClient {
	@GetMapping("/api/books")
	Page<BookContributorsResponse> getBooks(Pageable pageable);

	@GetMapping("/api/books/{book-id}")
	BookResponse getBookById(@PathVariable(name = "book-id") Long bookId,
		@RequestParam(value = "update-recent-view", required = false) boolean updateRecentView);

	@GetMapping("/api/categories")
	List<CategoryResponse> getAllCategories();

	// 알라딘 API 도서 등록
	@PostMapping("/api/books/register/aladin")
	ResponseEntity<ResponseMessage> registerAladinBook(@Valid @RequestBody AladinBookRegisterRequest registerRequest);

	// 직접 도서 등록
	@PostMapping("/api/books/register/personal")
	ResponseEntity<ResponseMessage> registerPersonallyBook(
		@Valid @RequestBody PersonallyBookRegisterRequest registerRequest);

	@GetMapping("/api/books/category/{category-id}")
	PagedResponse<BookContributorsResponse> getBooksByCategoryId(@PathVariable(name = "category-id") Long categoryId,
		@RequestParam("page") int page, @RequestParam("size") int size);
	
	@PutMapping("/api/books/{book-id}")
	ResponseEntity<ResponseMessage> updateBook(@PathVariable(name = "book-id") Long bookId,
		@Valid @RequestBody BookUpdateRequest bookUpdateRequest);

	@DeleteMapping("/api/books/{book-id}")
	Void deleteBook(@PathVariable(name = "book-id") Long bookId);

	@PostMapping(value = "/api/books/uploadImage", consumes = "multipart/form-data")
	String uploadImage(@RequestPart("file")MultipartFile file);

	@GetMapping("/api/books/top/book-like")
	List<TopBookLikeResponse> getTopBookLike();
}
