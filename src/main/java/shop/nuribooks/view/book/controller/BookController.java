package shop.nuribooks.view.book.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.book.dto.BookContributorsResponse;
import shop.nuribooks.view.book.dto.BookResponse;
import shop.nuribooks.view.book.service.BookService;
import shop.nuribooks.view.common.dto.PagedResponse;
import shop.nuribooks.view.review.dto.response.ReviewMemberResponse;
import shop.nuribooks.view.review.service.ReviewService;

@RequiredArgsConstructor
@Controller
public class BookController {
	private final BookService bookService;
	private final ReviewService reviewService;

	@GetMapping({"/view/books", "/admin/view/books"})
	public String getBooks(@RequestParam(defaultValue = "0") int page,
							@RequestParam(defaultValue = "10") int size,
							Model model) {
		PagedResponse<BookContributorsResponse> books = bookService.getBooks(page, size);
		model.addAttribute("books", books);
		return "book/bookList";
	}

	@GetMapping("/view/book/details/{bookId}")
	public String getBookById(@PathVariable Long bookId, Model model, @PageableDefault Pageable pageable) {
		BookResponse bookResponse = bookService.getBookById(bookId);
		model.addAttribute("book", bookResponse);
		Page<ReviewMemberResponse> pages = reviewService.getReviewsByBookId(bookId, pageable);
		model.addAttribute("pages", pages);
		model.addAttribute("type", "member");
		return "book/bookDetail";
	}
}
