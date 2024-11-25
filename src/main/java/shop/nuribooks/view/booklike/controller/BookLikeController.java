package shop.nuribooks.view.booklike.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.booklike.dto.BookLikeResponse;
import shop.nuribooks.view.booklike.dto.LikeStatusResponse;
import shop.nuribooks.view.booklike.service.BookLikeService;
import shop.nuribooks.view.common.dto.PagedResponse;

@Controller
@RequiredArgsConstructor
public class BookLikeController {
	private final BookLikeService bookLikeService;

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

	@GetMapping("/view/books/likes/me")
	public String getBookLikes(@PageableDefault Pageable pageable, Model model) {
		Page<BookLikeResponse> likeBooks = bookLikeService.getLikedBooks(pageable);
		model.addAttribute("likeBooks", likeBooks);
		model.addAttribute("pages", likeBooks);
		return "member/booklike/book-like-list";
	}

	@GetMapping("/view/books/likes/status/{book-id}")
	public ResponseEntity<LikeStatusResponse> getLikeStatus(@PathVariable(name = "book-id") Long bookId) {
		return ResponseEntity.ok(bookLikeService.getLikeStatus(bookId));
	}
}
