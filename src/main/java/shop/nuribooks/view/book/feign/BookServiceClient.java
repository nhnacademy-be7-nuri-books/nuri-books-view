package shop.nuribooks.view.book.feign;

import org.springdoc.core.converters.models.Pageable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import shop.nuribooks.view.book.dto.AdminBookListResponse;
import shop.nuribooks.view.book.dto.AladinBookRegisterRequest;
import shop.nuribooks.view.book.dto.BaseBookRegisterRequest;
import shop.nuribooks.view.book.dto.BookContributorsResponse;
import shop.nuribooks.view.book.dto.BookResponse;
import shop.nuribooks.view.book.dto.PersonallyBookRegisterRequest;
import shop.nuribooks.view.common.dto.PagedResponse;
import shop.nuribooks.view.common.dto.ResponseMessage;

@FeignClient(name = "book", url = "http://localhost:8080")
public interface BookServiceClient {
	@GetMapping("/api/books")
	PagedResponse<BookContributorsResponse> getBooks(@RequestParam("page") int page, @RequestParam("size") int size);

	@GetMapping("/api/books/{book-id}")
	BookResponse getBookById(@PathVariable(name = "book-id") Long bookId);

	// 알라딘 API 도서 등록
	@PostMapping("/api/books/register/aladin")
	ResponseEntity<ResponseMessage> registerAladinBook(@Valid @RequestBody AladinBookRegisterRequest registerRequest);

	// 직접 도서 등록
	@PostMapping("/api/books/register/personal")
	ResponseEntity<ResponseMessage> registerPersonallyBook(@Valid @RequestBody PersonallyBookRegisterRequest registerRequest);

	@GetMapping("/api/books/category/{category-id}")
	PagedResponse<BookContributorsResponse> getBooksByCategoryId(@PathVariable(name = "category-id") Long categoryId,
		@RequestParam("page") int page, @RequestParam("size") int size);
}
