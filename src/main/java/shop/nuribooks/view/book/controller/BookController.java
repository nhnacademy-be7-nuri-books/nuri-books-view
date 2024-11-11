package shop.nuribooks.view.book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.category.dto.CategoryRequest;
import shop.nuribooks.view.admin.category.service.AdminCategoryService;
import shop.nuribooks.view.book.dto.AdminBookListResponse;
import shop.nuribooks.view.book.dto.BookContributorsResponse;
import shop.nuribooks.view.book.dto.BookResponse;
import shop.nuribooks.view.book.service.BookService;
import shop.nuribooks.view.common.dto.PagedResponse;

@RequiredArgsConstructor
@Controller
public class BookController {
	private final BookService bookService;
	private final AdminCategoryService adminCategoryService;

	@GetMapping({"/view/books", "/admin/view/books"})
	public String getBooks(@RequestParam(defaultValue = "0") int page,
							@RequestParam(defaultValue = "10") int size,
							Model model) {
		PagedResponse<BookContributorsResponse> books = bookService.getBooks(page, size);
		model.addAttribute("books", books);
		return "book/bookList";
	}

	@GetMapping("/view/book/details/{book-id}")
	public String getBookById(@PathVariable(name = "book-id") Long bookId, Model model) {
		BookResponse bookResponse = bookService.getBookById(bookId);
		model.addAttribute("book", bookResponse);
		return "book/bookDetail";
	}

	@GetMapping("/view/books/categories/{category-id}")
	public String getBooksByCategoryId(@PathVariable(name = "category-id") Long categoryId,
								@RequestParam(defaultValue = "0") int page,
								@RequestParam(defaultValue = "10") int size, Model model) {
		CategoryRequest categoryName = adminCategoryService.getCategoryName(categoryId);
		PagedResponse<BookContributorsResponse> bookCategories = bookService.getBooksByCategoryId(categoryId, page, size);
		model.addAttribute("bookCategories", bookCategories);
		model.addAttribute("categoryName", categoryName);
		model.addAttribute("categoryId", categoryId);
		return "book/bookCategoryList";
	}
}
