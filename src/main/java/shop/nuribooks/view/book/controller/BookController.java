package shop.nuribooks.view.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.book.dto.AdminBookListResponse;
import shop.nuribooks.view.book.service.BookService;
import shop.nuribooks.view.common.dto.PagedResponse;

@RequiredArgsConstructor
@Controller
public class BookController {
	private final BookService bookService;

	@GetMapping("/view/books")
	public String getBooks(@RequestParam(defaultValue = "0") int page,
							@RequestParam(defaultValue = "10") int size,
							Model model) {
		PagedResponse<AdminBookListResponse> books = bookService.getBooks(page, size);
		model.addAttribute("books", books);
		return "book/bookList";
	}
}
