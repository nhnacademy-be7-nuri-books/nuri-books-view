package shop.nuribooks.view.admin.coupon.bookcoupon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import shop.nuribooks.view.admin.coupon.bookcoupon.dto.BookCouponRequest;
import shop.nuribooks.view.admin.coupon.bookcoupon.service.BookCouponService;
import shop.nuribooks.view.admin.coupon.dto.CouponRequest;
import shop.nuribooks.view.admin.coupon.service.CouponService;
import shop.nuribooks.view.common.dto.ResponseMessage;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/coupon")
public class BookCouponController {
	private final CouponService couponService;
	private final BookCouponService bookCouponService;

	@PostMapping("/book")
	public ResponseEntity<ResponseMessage> registerBookCoupon(
		@Valid @ModelAttribute BookCouponRequest bookCouponRequest) {
		ResponseMessage message = bookCouponService.registerBookCoupon(bookCouponRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	@PutMapping("/book-coupon/{coupon-id}")
	public ResponseEntity<ResponseMessage> updateBookCoupon(@PathVariable("coupon-id") Long id,
		@Valid @ModelAttribute CouponRequest couponRequest) {
		ResponseMessage message = couponService.updateCoupon(id, couponRequest);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}
}
