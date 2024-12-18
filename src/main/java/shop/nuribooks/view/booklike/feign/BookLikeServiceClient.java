package shop.nuribooks.view.booklike.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import shop.nuribooks.view.booklike.dto.BookLikeResponse;
import shop.nuribooks.view.booklike.dto.LikeStatusResponse;

@FeignClient(name = "book-like", url = "http://localhost:8080")
public interface BookLikeServiceClient {
	@PostMapping("/api/books/book-likes/{book-id}")
	void addLike(@PathVariable("book-id") long bookId);

	@DeleteMapping("/api/books/book-likes/{book-id}")
	void removeLike(@PathVariable("book-id") long bookId);

	@GetMapping("/api/books/book-likes/me")
	Page<BookLikeResponse> getBookLikes(@RequestParam("page") int page, @RequestParam("size") int size);

	@GetMapping("/api/books/book-likes/status/{book-id}")
	LikeStatusResponse getLikeStatus(@PathVariable("book-id") long bookId);
}
