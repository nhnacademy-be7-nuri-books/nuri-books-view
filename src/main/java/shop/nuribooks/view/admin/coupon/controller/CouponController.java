package shop.nuribooks.view.admin.coupon.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.category.dto.CategoryResponse;
import shop.nuribooks.view.admin.category.service.AdminCategoryService;
import shop.nuribooks.view.admin.coupon.dto.BookCouponRequest;
import shop.nuribooks.view.admin.coupon.dto.CategoryCouponRequest;
import shop.nuribooks.view.admin.coupon.dto.CouponRequest;
import shop.nuribooks.view.admin.coupon.dto.CouponResponse;
import shop.nuribooks.view.admin.coupon.enums.CouponType;
import shop.nuribooks.view.admin.coupon.enums.ExpirationType;
import shop.nuribooks.view.admin.coupon.service.CouponService;
import shop.nuribooks.view.admin.point.enums.PolicyType;
import shop.nuribooks.view.book.dto.BookResponse;
import shop.nuribooks.view.book.service.BookService;
import shop.nuribooks.view.common.dto.ResponseMessage;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/coupon")
public class CouponController {

	private final BookService bookService;
	private final CouponService couponService;
	private final AdminCategoryService adminCategoryService;

	/**
	 * 쿠폰 타입 별로 조회
	 * @param model
	 * @param pageable
	 * @param type
	 * @return
	 */
	@GetMapping
	public String getCoupons(Model model, @PageableDefault Pageable pageable,
		@RequestParam(name = "type", defaultValue = "ALL") CouponType type) {
		Page<CouponResponse> coupons = couponService.getCoupons(type, pageable);
		model.addAttribute("pages", coupons);
		model.addAttribute("policyTypes", PolicyType.values());
		model.addAttribute("couponTypes", CouponType.values());
		model.addAttribute("type", type);
		model.addAttribute("expirationTypes", ExpirationType.values());

		List<BookResponse> books = bookService.getAllBooks();
		model.addAttribute("books", books);
		return "admin/coupon/coupon";
	}

	/**
	 * 모든 쿠폰 한번에 조회
	 * @param model
	 * @param pageable
	 * @return
	 */
	@GetMapping("/list")
	public String getCoupons(Model model, @PageableDefault Pageable pageable) {
		Page<CouponResponse> coupons = this.couponService.getAllCoupons(pageable);
		model.addAttribute("pages", coupons);
		model.addAttribute("policyTypes", PolicyType.values());
		model.addAttribute("couponTypes", CouponType.values());
		model.addAttribute("expirationTypes", ExpirationType.values());

		List<BookResponse> books = bookService.getAllBooks();
		model.addAttribute("books", books);
		return "admin/coupon/coupon";
	}

	/**
	 * 쿠폰 상세 조회
	 * @param couponId
	 * @param model
	 * @return
	 */
	@GetMapping("/detail/{coupon-id}")
	public String getCouponDetail(@PathVariable(name = "coupon-id") Long couponId,
		Model model) {
		CouponResponse couponResponse = couponService.getCouponById(couponId);
		model.addAttribute("coupon", couponResponse);
		model.addAttribute("policyTypes", PolicyType.values());
		model.addAttribute("couponTypes", CouponType.values());
		model.addAttribute("expirationTypes", ExpirationType.values());

		return "admin/coupon/coupon_detail";
	}

	@GetMapping("/{couponId}")
	public ResponseEntity<CouponResponse> getCoupon(@PathVariable Long couponId) {
		CouponResponse couponResponse = couponService.getCouponById(couponId);
		return ResponseEntity.ok(couponResponse);
	}

	@PostMapping
	public ResponseEntity<ResponseMessage> registerCoupon(@Valid @ModelAttribute CouponRequest couponRequest) {
		ResponseMessage message = couponService.registerCoupon(couponRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	@PostMapping("/book")
	public ResponseEntity<ResponseMessage> registerBookCoupon(
		@Valid @ModelAttribute BookCouponRequest bookCouponRequest) {
		ResponseMessage message = couponService.registerBookCoupon(bookCouponRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	@PostMapping("/category")
	public ResponseEntity<ResponseMessage> registerCategoryCoupon(
		@Valid @ModelAttribute CategoryCouponRequest couponRequest
	) {
		ResponseMessage message = couponService.registerCategoryCoupon(couponRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	@PutMapping("/{coupon-id}")
	public ResponseEntity<ResponseMessage> updateCoupon(@PathVariable("coupon-id") Long id,
		@Valid @ModelAttribute CouponRequest couponRequest) {
		ResponseMessage message = couponService.updateCoupon(id, couponRequest);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@PutMapping("/book-coupon/{coupon-id}")
	public ResponseEntity<ResponseMessage> updateBookCoupon(@PathVariable("coupon-id") Long id,
		@Valid @ModelAttribute CouponRequest couponRequest) {
		ResponseMessage message = couponService.updateCoupon(id, couponRequest);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@PutMapping("/category-coupon/{coupon-id}")
	public ResponseEntity<ResponseMessage> updateCategoryCoupon(@PathVariable("coupon-id") Long id,
		@Valid @ModelAttribute CouponRequest couponRequest) {
		ResponseMessage message = couponService.updateCoupon(id, couponRequest);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@PutMapping("/{coupon-id}/expire")
	public ResponseEntity<ResponseMessage> expireCoupon(@PathVariable("coupon-id") Long id) {
		ResponseMessage message = couponService.expireCoupon(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
	}

	@GetMapping("/categories")
	public String getAllCategories(Model model) {
		List<CategoryResponse> categoryList = adminCategoryService.getAllCategories();
		model.addAttribute("categories", categoryList);
		return "admin/coupon/modal/category_modal";
	}

}
