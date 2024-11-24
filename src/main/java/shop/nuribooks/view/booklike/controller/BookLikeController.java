package shop.nuribooks.view.booklike.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.booklike.dto.LikeStatusResponse;
import shop.nuribooks.view.booklike.service.BookLikeService;

@Controller
@RequiredArgsConstructor
public class BookLikeController {
	private final BookLikeService bookLikeService;

	@Value("${success.message-key}")
	private String successMessageKey;

	@Value("${error.message-key}")
	private String errorMessageKey;

	@PostMapping("/view/books/likes/{book-id}")
	public ResponseEntity<String> addLike(@PathVariable(name = "book-id") Long bookId) {
		bookLikeService.addLike(bookId);
		return ResponseEntity.ok("좋아요가 추가되었습니다");
	}

	@DeleteMapping("/view/books/likes/{book-id}")
	public ResponseEntity<String> removeLike(@PathVariable(name = "book-id") Long bookId) {
		bookLikeService.removeLike(bookId);
		return ResponseEntity.ok("좋아요가 취소되었습니다");
	}

	@GetMapping("/view/books/likes/status/{book-id}")
	public ResponseEntity<LikeStatusResponse> getLikeStatus(@PathVariable(name = "book-id") Long bookId) {
		return ResponseEntity.ok(bookLikeService.getLikeStatus(bookId));
	}
}
