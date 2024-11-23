package shop.nuribooks.view.book.controller;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.category.dto.CategoryRequest;
import shop.nuribooks.view.admin.category.dto.CategoryResponse;
import shop.nuribooks.view.admin.category.service.AdminCategoryService;
import shop.nuribooks.view.book.dto.BookContributorsResponse;
import shop.nuribooks.view.book.dto.BookResponse;
import shop.nuribooks.view.book.service.BookService;
import shop.nuribooks.view.common.dto.PagedResponse;
import shop.nuribooks.view.common.util.CookieUtil;
import shop.nuribooks.view.common.util.TimeUtil;
import shop.nuribooks.view.review.dto.response.ReviewMemberResponse;
import shop.nuribooks.view.review.service.ReviewService;

@RequiredArgsConstructor
@Controller
public class BookController {
	private final BookService bookService;
	private final ReviewService reviewService;
	private final AdminCategoryService adminCategoryService;
	private final String RECENT_VIEW_LIST_KEY = "recent_view_list";
	private final int RECENT_VIEW_LIST_SIZE = 10;

	@GetMapping("/view/books")
	public String getBooks(@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size,
		Model model) {
		PagedResponse<BookContributorsResponse> books = bookService.getBooks(page, size);
		model.addAttribute("books", books);
		model.addAttribute("isAdmin", false);
		model.addAttribute("layout", "layouts/layout1");  // 일반 사용자 레이아웃
		return "book/bookList";
	}

	@GetMapping("/view/book/details/{bookId}")
	public String getBookById(@PathVariable Long bookId,
		Model model,
		@PageableDefault Pageable pageable,
		HttpServletRequest req,
		HttpServletResponse res) {
		LinkedHashSet<Long> recentViewSet = getRecentViewSet(req);

		BookResponse bookResponse = bookService.getBookByIdAndUpdateRecentView(bookId, recentViewSet);
		model.addAttribute("book", bookResponse);

		// recent view set 크기를 유지하며 새로운 id 추가.
		recentViewSet.remove(bookId);
		recentViewSet.add(bookId);
		while (recentViewSet.size() > RECENT_VIEW_LIST_SIZE) {
			recentViewSet.removeFirst();
		}

		Page<ReviewMemberResponse> pages = reviewService.getReviewsByBookId(bookId, pageable);
		model.addAttribute("pages", pages);
		model.addAttribute("type", "member");

		// 쿠키에 새로운 최근 목록 업데이트. TTL = 발행일 자정까지 .
		CookieUtil.addCookie(res, RECENT_VIEW_LIST_KEY, getRecentViewSetString(recentViewSet),
			(int)TimeUtil.getLeftSecondOfToday());
		return "book/bookDetail";
	}

	@GetMapping("/view/books/categories/{category-id}")
	public String getBooksByCategoryId(@PathVariable(name = "category-id") Long categoryId,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size, Model model) {
		CategoryRequest categoryName = adminCategoryService.getCategoryName(categoryId);
		PagedResponse<BookContributorsResponse> bookCategories = bookService.getBooksByCategoryId(categoryId, page,
			size);
		model.addAttribute("bookCategories", bookCategories);
		model.addAttribute("categoryName", categoryName);
		model.addAttribute("categoryId", categoryId);
		return "book/bookCategoryList";
	}

	@GetMapping("/view/categories")
	public String getAllCategories(Model model) {
		List<CategoryResponse> categoryList = adminCategoryService.getAllCategories();
		model.addAttribute("categories", categoryList);
		return "book/category/all-category";
	}

	private String ExtractValueFromCookie(HttpServletRequest req, String key) {
		Cookie[] cookies = req.getCookies();
		for (int i = 0; cookies != null && i < cookies.length; i++) {
			if (cookies[i].getName().equals(key)) {
				return cookies[i].getValue();
			}
		}
		return null;
	}

	private LinkedHashSet<Long> getRecentViewSet(HttpServletRequest req) {
		String recentViewListString = ExtractValueFromCookie(req, RECENT_VIEW_LIST_KEY);

		LinkedHashSet<Long> recentViewSet = new LinkedHashSet<>();

		if (recentViewListString != null) {
			String[] recentViewList = recentViewListString.split("%");
			for (String str : recentViewList) {
				try {
					recentViewSet.add(Long.parseLong(str));
				} catch (NumberFormatException e) {
				}
			}
		}

		return recentViewSet;
	}

	private String getRecentViewSetString(Set<Long> recentViewSet) {
		return String.join("%", recentViewSet.stream().map(l -> l.toString()).toList());
	}
}
