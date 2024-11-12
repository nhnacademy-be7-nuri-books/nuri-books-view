package shop.nuribooks.view.book.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.category.dto.CategoryResponse;
import shop.nuribooks.view.admin.category.dto.CategoryTreeResponse;
import shop.nuribooks.view.admin.category.service.AdminCategoryService;
import shop.nuribooks.view.admin.tag.dto.TagResponse;
import shop.nuribooks.view.admin.tag.service.TagService;
import shop.nuribooks.view.book.dto.AladinBookListItemResponse;
import shop.nuribooks.view.book.dto.AladinBookRegisterRequest;
import shop.nuribooks.view.book.dto.BookContributorsResponse;
import shop.nuribooks.view.book.dto.PersonallyBookRegisterRequest;
import shop.nuribooks.view.book.service.AladinBookService;
import shop.nuribooks.view.book.service.BookService;
import shop.nuribooks.view.common.dto.PagedResponse;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/view")
public class AdminBookController {
	private final AladinBookService aladinBookService;
	private final BookService bookService;
	private final TagService tagService;
	private final AdminCategoryService adminCategoryService;

	/**
	 *
	 * @return 관리자용 도서 관리 페이지
	 */
	@GetMapping("/book/manage")
	public String showBookManagePage() {
		return "book/adminBookManage";
	}

	/**
	 *
	 * @return 알라딘 api를 이용한 도서 목록 불러오기 페이지
	 */
	@GetMapping("/aladin/books")
	public String showAladinBookPage() {
		return "book/aladinBookList";
	}

	/**
	 *
	 * @param queryType 조회할 도서 목록 유형
	 * @param searchTarget 조회할 도서의 대상
	 * @param maxResults 한 번에 조회할 도서 개수
	 * @param model 뷰로 데이터를 전달하기 위한 모델 객체
	 * @return 알라딘 api를 이용한 도서 목록을 화면에 출력
	 */
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

	/**
	 *
	 * @return 알라딘 api로 부터 얻어온 정보와 함께 등록 폼 반환
	 */
	@GetMapping("/aladin/book/prepare-register/{isbn}")
	public String getRegisterBookByIsbn(@PathVariable String isbn, Model model) {
		AladinBookListItemResponse book = aladinBookService.getAladinBookByIsbn(isbn);
		prepareBookRegisterForm(book, model);
		model.addAttribute("isAladinMode", true);
		return "/book/bookRegister";
	}

	/**
	 *
	 * @return 도서 등록 폼으로 이동
	 */
	@GetMapping("/book/register-personally")
	public String showBookRegisterPage(Model model) {
		AladinBookListItemResponse book = AladinBookListItemResponse.empty();
		prepareBookRegisterForm(book, model);
		model.addAttribute("isAladinMode", false);
		return "/book/bookRegister";
	}

	/**
	 * 알라딘 API를 이용한 도서 등록
	 */
	@PostMapping("/aladin/book")
	public String registerAladinBook(@ModelAttribute AladinBookRegisterRequest aladinRegisterReq, RedirectAttributes redirectAttributes) {
		try {
			bookService.registerAladinBook(aladinRegisterReq);
			redirectAttributes.addFlashAttribute("successMessage", "도서 등록 성공");
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("errorMessage", "도서 등록 실패");
		}
		return "redirect:/admin/view/aladin/books";
	}

	/**
	 * 직접 도서 등록
	 */
	@PostMapping("/personally/book")
	public String registerPersonallyBook(@ModelAttribute PersonallyBookRegisterRequest personallyRegisterReq, RedirectAttributes redirectAttributes) {
		try {
			bookService.registerPersonallyBook(personallyRegisterReq);
			redirectAttributes.addFlashAttribute("successMessage", "도서 등록 성공");
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("errorMessage", "도서 등록 실패");
		}
		return "redirect:/admin/book/adminBookManage";
	}

	@GetMapping("/books")
	public String getAdminBooks(@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		Model model) {
		PagedResponse<BookContributorsResponse> books = bookService.getBooks(page, size);
		model.addAttribute("books", books);
		model.addAttribute("isAdmin", true);
		model.addAttribute("layout", "admin/layout/adminlayout");
		return "book/bookList";
	}

	@DeleteMapping("/book/{book-id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteBook(@PathVariable(name = "book-id") Long bookId) {
		bookService.deleteBook(bookId);
	}

	/**
	 * book 등록 폼에 필요한 공통 데이터를 설정합니다.
	 *
	 * @param book 책 정보 객체
	 * @param model 모델 객체
	 */
	private void prepareBookRegisterForm(AladinBookListItemResponse book, Model model) {
		List<CategoryTreeResponse> categories = adminCategoryService.getAllCategoryTree();
		List<TagResponse> tags = tagService.getAllTags();

		model.addAttribute("book", book);
		model.addAttribute("categories", categories);
		model.addAttribute("tags", tags);
	}
}
