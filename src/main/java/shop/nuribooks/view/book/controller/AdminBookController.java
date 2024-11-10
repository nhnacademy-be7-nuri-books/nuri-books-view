package shop.nuribooks.view.book.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.tag.dto.TagResponse;
import shop.nuribooks.view.admin.tag.service.TagService;
import shop.nuribooks.view.book.dto.AladinBookListItemResponse;
import shop.nuribooks.view.book.dto.AladinBookSaveRequest;
import shop.nuribooks.view.book.service.AladinBookService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/view")
public class AdminBookController {
	private final AladinBookService aladinBookService;
	private final TagService tagService;

	@GetMapping("/books")
	public String showAladinBookPage() {
		return "book/aladinBookList";
	}

	@GetMapping("/aladin/books/list")
	public String getAladinBookList(@RequestParam(defaultValue = "ItemNewAll") String queryType,
									@RequestParam(defaultValue = "Book") String searchTarget,
									@RequestParam(defaultValue = "10") int maxResults,
									Model model) {
		List<AladinBookListItemResponse> books = aladinBookService.getAladinBookList(queryType, searchTarget, maxResults);
		model.addAttribute("books", books);
		model.addAttribute("queryType", queryType);
		model.addAttribute("searchTarget", searchTarget);
		model.addAttribute("maxResults", maxResults);
		return "book/aladinBookList";
	}

	@GetMapping("/aladin/book/prepare-register/{isbn}")
	public String getRegisterBookByIsbn(@PathVariable String isbn, Model model) {
		AladinBookListItemResponse book = aladinBookService.getAladinBookByIsbn(isbn);
		List<TagResponse> tags = tagService.getAllTags();

		model.addAttribute("book", book);
		model.addAttribute("tags", tags);
		return "book/bookRegister";
	}

	@PostMapping("/aladin/book/save")
	public String saveBook(@ModelAttribute AladinBookSaveRequest aladinBookSaveRequest, RedirectAttributes redirectAttributes) {
		aladinBookService.saveAladinBook(aladinBookSaveRequest);
		redirectAttributes.addFlashAttribute("successMessage", "도서 등록 성공");
		return "redirect:/admin/view/aladin/books";
	}
}
