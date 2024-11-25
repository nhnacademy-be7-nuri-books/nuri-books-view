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

	@GetMapping
	public String getCoupons(Model model, @PageableDefault Pageable pageable,
		@RequestParam(value = "type", defaultValue = "ALL") CouponType type) {
		Page<CouponResponse> coupons = this.couponService.getCoupons(type, pageable);
		model.addAttribute("pages", coupons);
		model.addAttribute("policyTypes", PolicyType.values());
		model.addAttribute("couponTypes", CouponType.values());
		model.addAttribute("type", type);
		model.addAttribute("expirationTypes", ExpirationType.values());

		List<BookResponse> books = this.bookService.getAllBooks();
		model.addAttribute("books", books);
		return "admin/coupon/coupon";
	}

	@PostMapping
	public ResponseEntity<ResponseMessage> registerCoupon(@Valid @ModelAttribute CouponRequest couponRequest) {
		ResponseMessage message = this.couponService.registerCoupon(couponRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	@PostMapping("/book")
	public ResponseEntity<ResponseMessage> registerBookCoupon(
		@Valid @ModelAttribute BookCouponRequest bookCouponRequest) {
		ResponseMessage message = this.couponService.registerBookCoupon(bookCouponRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	@PostMapping("/category")
	public ResponseEntity<ResponseMessage> registerCategoryCoupon(
		@Valid @ModelAttribute CategoryCouponRequest couponRequest
	) {
		ResponseMessage message = this.couponService.registerCategoryCoupon(couponRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	@PutMapping("/{coupon-id}")
	public ResponseEntity<ResponseMessage> updateCoupon(@PathVariable("coupon-id") Long id,
		@Valid @ModelAttribute CouponRequest couponRequest) {
		ResponseMessage message = this.couponService.updateCoupon(id, couponRequest);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

	@PutMapping("/{coupon-id}/expire")
	public ResponseEntity<ResponseMessage> expireCoupon(@PathVariable("coupon-id") Long id) {
		ResponseMessage message = this.couponService.expireCoupon(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
	}

	@GetMapping("/categories")
	public String getAllCategories(Model model) {
		List<CategoryResponse> categoryList = adminCategoryService.getAllCategories();
		model.addAttribute("categories", categoryList);
		return "admin/coupon/modal/category_modal";
	}

}
