package shop.nuribooks.view.admin.coupon.categorycoupon.controller;

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
import shop.nuribooks.view.admin.category.service.AdminCategoryService;
import shop.nuribooks.view.admin.coupon.categorycoupon.dto.CategoryCouponRequest;
import shop.nuribooks.view.admin.coupon.categorycoupon.service.CategoryCouponService;
import shop.nuribooks.view.admin.coupon.dto.CouponRequest;
import shop.nuribooks.view.admin.coupon.service.CouponService;
import shop.nuribooks.view.common.dto.ResponseMessage;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/coupon")
public class CategoryCouponController {
	private final CouponService couponService;
	private final CategoryCouponService categoryCouponService;
	private final AdminCategoryService adminCategoryService;

	@PostMapping("/category")
	public ResponseEntity<ResponseMessage> registerCategoryCoupon(
		@Valid @ModelAttribute CategoryCouponRequest couponRequest
	) {
		ResponseMessage message = categoryCouponService.registerCategoryCoupon(couponRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(message);
	}

	@PutMapping("/category-coupon/{coupon-id}")
	public ResponseEntity<ResponseMessage> updateCategoryCoupon(@PathVariable("coupon-id") Long id,
		@Valid @ModelAttribute CouponRequest couponRequest) {
		ResponseMessage message = couponService.updateCoupon(id, couponRequest);
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}

}
