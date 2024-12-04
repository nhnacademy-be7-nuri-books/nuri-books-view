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
import lombok.extern.slf4j.Slf4j;
import shop.nuribooks.view.admin.category.dto.CategoryRequest;
import shop.nuribooks.view.admin.category.dto.CategoryResponse;
import shop.nuribooks.view.admin.category.service.AdminCategoryService;
import shop.nuribooks.view.admin.coupon.bookcoupon.dto.BookCouponResponse;
import shop.nuribooks.view.admin.coupon.bookcoupon.service.BookCouponService;
import shop.nuribooks.view.admin.coupon.categorycoupon.dto.CategoryCouponResponse;
import shop.nuribooks.view.admin.coupon.categorycoupon.service.CategoryCouponService;
import shop.nuribooks.view.admin.coupon.service.CouponService;
import shop.nuribooks.view.book.dto.BookContributorsResponse;
import shop.nuribooks.view.book.dto.BookResponse;
import shop.nuribooks.view.book.enums.SortType;
import shop.nuribooks.view.book.service.BookService;
import shop.nuribooks.view.booklike.dto.LikeStatusResponse;
import shop.nuribooks.view.booklike.service.BookLikeService;
import shop.nuribooks.view.common.dto.PagedResponse;
import shop.nuribooks.view.common.util.CookieUtil;
import shop.nuribooks.view.common.util.TimeUtil;
import shop.nuribooks.view.review.dto.response.ReviewMemberResponse;
import shop.nuribooks.view.review.dto.response.ReviewScoreResponse;
import shop.nuribooks.view.review.service.ReviewService;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BookController {
	private final BookService bookService;
	private final ReviewService reviewService;
	private final AdminCategoryService adminCategoryService;
	private final BookLikeService bookLikeService;
	private final CouponService couponService;
	private final BookCouponService bookCouponService;
	private final CategoryCouponService categoryCouponService;
	private final String RECENT_VIEW_LIST_KEY = "recent_view_list";

	@GetMapping("/view/books")
	public String getBooks(@PageableDefault Pageable pageable,
		Model model) {
		Page<BookContributorsResponse> books = bookService.getBooks(pageable);
		model.addAttribute("pages", books);
		model.addAttribute("sort_types", SortType.values());
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
		Set<Long> recentViewSet = getRecentViewSet(req);

		BookResponse bookResponse = bookService.getBookByIdAndUpdateRecentView(bookId, recentViewSet);
		model.addAttribute("book", bookResponse);

		// recent view 이전 방문 id 삭제 후 id 순서 최신화
		recentViewSet.remove(bookId);
		recentViewSet.add(bookId);

		Page<ReviewMemberResponse> pages = reviewService.getReviewsByBookId(bookId, pageable);
		model.addAttribute("pages", pages);
		model.addAttribute("type", "member");

		LikeStatusResponse likeStatus = bookLikeService.getLikeStatus(bookId);
		model.addAttribute("likeStatus", likeStatus);

		ReviewScoreResponse scoreResponse = reviewService.getScoreByBookId(bookId);
		model.addAttribute("score", scoreResponse);

		try {
			BookCouponResponse bookCoupon = bookCouponService.getBookCoupon(bookId);
			model.addAttribute("bookCoupon", bookCoupon);
		} catch (Exception e) {
			model.addAttribute("bookCoupon", null);
		}

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

		try {
			CategoryCouponResponse categoryCoupon = categoryCouponService.getCategoryCoupon(categoryId);
			model.addAttribute("categoryCoupon", categoryCoupon);
		} catch (Exception e) {
			model.addAttribute("categoryCoupon", null);
		}
		return "book/bookCategoryList";
	}

	@GetMapping("/view/categories")
	public String getAllCategories(Model model) {
		List<CategoryResponse> categoryList = adminCategoryService.getAllCategories();
		model.addAttribute("categories", categoryList);
		return "book/category/all-category";
	}

	@GetMapping("/all")
	public List<BookResponse> getAllBooks() {
		return bookService.getAllBooks();
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

	private Set<Long> getRecentViewSet(HttpServletRequest req) {
		String recentViewListString = ExtractValueFromCookie(req, RECENT_VIEW_LIST_KEY);

		Set<Long> recentViewSet = new LinkedHashSet<>();

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
