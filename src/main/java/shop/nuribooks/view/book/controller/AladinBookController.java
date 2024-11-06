package shop.nuribooks.view.book.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.book.dto.AladinBookListItemResponse;
import shop.nuribooks.view.book.dto.AladinBookSaveRequest;
import shop.nuribooks.view.book.service.AladinBookService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/view/aladin")
public class AladinBookController {
	private final AladinBookService aladinBookService;

	@GetMapping("/books")
	public String showAladinBookPage() {
		return "book/aladinBookList";
	}

	@GetMapping("/books/list")
	public String getAladinBookList(@RequestParam(defaultValue = "ItemNewAll") String queryType,
									@RequestParam(defaultValue = "Book") String searchTarget,
									@RequestParam(defaultValue = "10") int maxResults,
									Model model) {
		List<AladinBookListItemResponse> books = aladinBookService.getAladinBookList(queryType, searchTarget, maxResults);
		model.addAttribute("books", books);
		return "book/aladinBookList";
	}

	@GetMapping("/book/register/{isbn}")
	public String getEditBookByIsbn(@PathVariable String isbn, Model model) {
		AladinBookListItemResponse book = aladinBookService.getAladinBookByIsbn(isbn);
		model.addAttribute("book", book);
		return "book/bookRegister";
	}

	@PostMapping("/book/save")
	public String saveBook(@Valid @RequestBody AladinBookSaveRequest aladinBookSaveRequest, RedirectAttributes redirectAttributes) {
		aladinBookService.saveAladinBook(aladinBookSaveRequest);
		redirectAttributes.addFlashAttribute("successMessage", "도서 등록 성공");
		return "redirect:/api/view/aladin/books";
	}
}
